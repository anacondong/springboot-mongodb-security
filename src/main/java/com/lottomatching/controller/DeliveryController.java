package com.lottomatching.controller;

import com.lottomatching.domain.*;
import com.lottomatching.service.CustomUserDetailsService;
import com.lottomatching.service.DeliveryService;
import com.lottomatching.service.LottoService;
import com.lottomatching.service.RoundService;
import com.lottomatching.utils.Utils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class DeliveryController {

    @Autowired
    private LottoService lottoService;

    @Autowired
    private CustomUserDetailsService userService;

    @Autowired
    private RoundService roundService;

    @Autowired
    private DeliveryService deliveryService;

    @RequestMapping(value = "/admin/delivery", method = RequestMethod.GET)
    public ModelAndView deliveryHome() {
        ModelAndView modelAndView = new ModelAndView();

        Utils.setCurrentUser(userService, modelAndView);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByEmail(auth.getName());
        List<Round> roundList = roundService.findByStatus("process");
        List<Delivery> deliveryList = new ArrayList<Delivery>();

        for(Round round: roundList){
            deliveryList.addAll(deliveryService.findByRoundOrderByStatusDesc(round.getNumber()));
        }

        int lottosRound = 0;
        int lottosRoundMatched = 0;
        int lottosRoundNotmatched = 0;
        int lottosRoundReceived = 0;
        for(Delivery d: deliveryList){
            lottosRound = lottosRound + d.getLottoList();
            lottosRoundMatched = lottosRoundMatched + d.getMatched();
            lottosRoundNotmatched = lottosRoundNotmatched + d.getNotMatched();
            lottosRoundReceived = lottosRoundReceived+ d.getReceived();
        }
        modelAndView.addObject("lottosRound", lottosRound);
        modelAndView.addObject("lottosRoundMatched", lottosRoundMatched);
        modelAndView.addObject("lottosRoundNotmatched", lottosRoundNotmatched);
        modelAndView.addObject("lottosRoundReceived", lottosRoundReceived);
        modelAndView.addObject("deliveryList", deliveryList);
        modelAndView.addObject("roundList", roundList);
        modelAndView.setViewName("delivery/list");
        return modelAndView;
    }


    @RequestMapping(value = "/admin/delivery/{id}", method = RequestMethod.GET)
    public ModelAndView deliveryFindId(@PathVariable("id") String id) {

        Delivery delivery = deliveryService.findById(new ObjectId(id));
        ModelAndView modelAndView = new ModelAndView();
        Utils.setCurrentUser(userService, modelAndView);


        List<Round> roundList = roundService.findByStatus("process");
        List<Lotto> userLotto = new ArrayList<Lotto>();
        List<Lotto> matchedLotto = new ArrayList<Lotto>();
        for(Round round: roundList){
            userLotto.addAll(lottoService.findByUserAndRoundAndEnabledOrderByIdDesc(delivery.getUser(),round.getNumber(), true));
            matchedLotto.addAll(lottoService.findByUserAndRoundAndMatch(delivery.getUser(), round.getNumber(),true));
        }

        // set to display order grouping page
        Utils.setMatchedLottoToGroup(matchedLotto, modelAndView);

        modelAndView.addObject("userLotto", userLotto);
        modelAndView.addObject("matchedLotto", matchedLotto);
        modelAndView.addObject("roundList", roundList);
        modelAndView.addObject("delivery", delivery);
        modelAndView.setViewName("delivery/delivery");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/delivery", method = RequestMethod.POST)
    public ModelAndView deliveryUpdate(@RequestParam("id") String id, @RequestParam("status") String status) {
        Delivery delivery = deliveryService.findById(new ObjectId(id));
        delivery.setStatus(status);
        deliveryService.save(delivery);

        ModelAndView modelAndView = new ModelAndView();
        Utils.setCurrentUser(userService, modelAndView);
        List<Round> roundList = roundService.findByStatus("process");
        List<Lotto> userLotto = new ArrayList<Lotto>();
        List<Lotto> matchedLotto = new ArrayList<Lotto>();
        for(Round round: roundList){
            userLotto.addAll(lottoService.findByUserAndRoundAndEnabledOrderByIdDesc(delivery.getUser(),round.getNumber(), true));
            matchedLotto.addAll(lottoService.findByUserAndRoundAndMatch(delivery.getUser(), round.getNumber(),true));
        }

        delivery = deliveryService.findById(delivery.getId());

        // set to display order grouping page
        Utils.setMatchedLottoToGroup(matchedLotto, modelAndView);
        modelAndView.addObject("userLotto", userLotto);
        modelAndView.addObject("matchedLotto", matchedLotto);
        modelAndView.addObject("roundList", roundList);
        modelAndView.addObject("delivery", delivery);
        modelAndView.addObject("message", "สำเร็จ");
        modelAndView.setViewName("delivery/delivery");
        return modelAndView;
    }


    @RequestMapping(value = "/admin/delivery/receive", method = RequestMethod.POST)
    public ModelAndView deliveryReceive(@RequestParam("id") String id, @RequestParam("barcode") String barcode) {
        Delivery delivery = deliveryService.findById(new ObjectId(id));
        List<Lotto> userLottoReceived = lottoService.findByUserAndRoundAndMatch(delivery.getUser(),delivery.getRound(),true);
        List<String> barcodeList = Arrays.asList(barcode.split("\\r?\\n"));
        int received = 0;

        for(String bc: barcodeList) {
            for (Lotto lotto : userLottoReceived) {
                if (lotto.getBarcode().equals(bc)) {
                    lotto.setReceived(true);
                    received = received + lottoService.save(lotto);
                }
            }
        }
        received = received + delivery.getReceived();
        delivery.setReceived(received);
        deliveryService.save(delivery);
        delivery = deliveryService.findById(delivery.getId());

        ModelAndView modelAndView = new ModelAndView();
        Utils.setCurrentUser(userService, modelAndView);
        List<Round> roundList = roundService.findByStatus("process");
        List<Lotto> userLotto = new ArrayList<Lotto>();
        List<Lotto> matchedLotto = new ArrayList<Lotto>();
        for(Round round: roundList){
            userLotto.addAll(lottoService.findByUserAndRoundAndEnabledOrderByIdDesc(delivery.getUser(),round.getNumber(), true));
            matchedLotto.addAll(lottoService.findByUserAndRoundAndMatch(delivery.getUser(), round.getNumber(),true));
        }

        // set to display order grouping page
        Utils.setMatchedLottoToGroup(matchedLotto, modelAndView);
        modelAndView.addObject("userLotto", userLotto);
        modelAndView.addObject("matchedLotto", matchedLotto);
        modelAndView.addObject("roundList", roundList);
        modelAndView.addObject("delivery", delivery);

        if(received > 0) {
            modelAndView.addObject("message", "ส่งทั้งหมด "+barcodeList.size()+" ตรวจสอบสำเร็จ "+received+" จำนวน  ไม่สำเร็จจำนวน "+(barcodeList.size() - received));
        } else {
            modelAndView.addObject("message", "ไม่สำเร็จ โปรดตรวจสอบข้อมูล");
        }

        modelAndView.setViewName("delivery/delivery");
        return modelAndView;
    }

}
