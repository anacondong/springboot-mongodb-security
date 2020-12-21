package com.lottomatching.controller;

import com.lottomatching.domain.Lotto;
import com.lottomatching.domain.News;
import com.lottomatching.domain.Round;
import com.lottomatching.domain.User;
import com.lottomatching.service.CustomUserDetailsService;
import com.lottomatching.service.LottoService;
import com.lottomatching.service.NewsService;
import com.lottomatching.service.RoundService;
import com.lottomatching.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
public class LottoController {

    @Autowired
    private LottoService lottoService;

    @Autowired
    private CustomUserDetailsService userService;

    @Autowired
    private RoundService roundService;

    @RequestMapping(value = "/user/lotto", method = RequestMethod.GET)
    public ModelAndView lottoList() {
        ModelAndView modelAndView = new ModelAndView();

        Utils.setCurrentUser(userService, modelAndView);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByEmail(auth.getName());
        List<Round> roundList = roundService.findByStatus("open");
        List<Lotto> lottos = new ArrayList<Lotto>();
        for(Round round: roundList){
            lottos.addAll(lottoService.findByUserAndRoundAndEnabledOrderByIdDesc(currentUser,round.getNumber(), true));
        }

        modelAndView.addObject("lottos", lottos);
        modelAndView.addObject("roundList", roundList);
        modelAndView.setViewName("lotto/list");
        return modelAndView;
    }

    @RequestMapping(value = "/user/lotto/add", method = RequestMethod.POST)
    public ModelAndView lottoAdd(@RequestParam("barcode") String barcode) {

        ModelAndView modelAndView = new ModelAndView();
        Utils.setCurrentUser(userService, modelAndView);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByEmail(auth.getName());
        List<Round> roundList = roundService.findByStatus("open");

        int sendCount = 0;
        int count = 0;
        try {
            List<String> barcodeList = Arrays.asList(barcode.split("\\r?\\n"));
            count = barcodeList.size();
            boolean hasMatchRound = false;
            for (String b : barcodeList) {
                Lotto lotto = new Lotto();
                lotto.setBarcode(b);
                lotto.setRound(b.substring(2, 4));
                lotto.setGroup(b.substring(4, 6));
                lotto.setNumber(b.substring(6, 10));
                lotto.setDate(new Date());
                lotto.setEnabled(true);
                lotto.setUser(currentUser);
                lotto.setMatch(false);
                lotto.setReceived(false);

                // save only round status is open
                for(Round r: roundList){
                    if (r.getNumber().equals(lotto.getRound())) {
                        hasMatchRound =true;
                    }
                }
                if(hasMatchRound) {
                    sendCount = sendCount + lottoService.save(lotto);
                }
            }
        } catch (Exception e) {
            modelAndView.addObject("messageError", "ดำเนินการไม่สำเร็จ กรุณาลองไหม่");
        }

        List<Lotto> lottos = new ArrayList<Lotto>();
        for(Round round: roundList){
            lottos.addAll(lottoService.findByUserAndRoundAndEnabledOrderByIdDesc(currentUser,round.getNumber(), true));
        }

        modelAndView.addObject("message", "จำนวนที่ส่งได้: " + sendCount +" จากทั้งหมด: " + count);
        modelAndView.addObject("lottos", lottos);
        modelAndView.addObject("roundList", roundList);
        modelAndView.setViewName("lotto/list");
        return modelAndView;
    }

}
