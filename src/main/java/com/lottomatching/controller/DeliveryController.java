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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        modelAndView.addObject("delivery", delivery);
        modelAndView.addObject("message", "สำเร็จ");
        modelAndView.setViewName("delivery/delivery");
        return modelAndView;
    }

}
