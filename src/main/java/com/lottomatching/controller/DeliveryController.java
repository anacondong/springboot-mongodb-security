package com.lottomatching.controller;

import com.lottomatching.domain.Delivery;
import com.lottomatching.domain.Lotto;
import com.lottomatching.domain.Round;
import com.lottomatching.domain.User;
import com.lottomatching.service.CustomUserDetailsService;
import com.lottomatching.service.DeliveryService;
import com.lottomatching.service.LottoService;
import com.lottomatching.service.RoundService;
import com.lottomatching.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
        for(Delivery d: deliveryList){
            lottosRound = lottosRound + d.getLottoList();
            lottosRoundMatched = lottosRoundMatched + d.getMatched();
            lottosRoundNotmatched = lottosRoundNotmatched + d.getNotMatched();
        }
        modelAndView.addObject("lottosRound", lottosRound);
        modelAndView.addObject("lottosRoundMatched", lottosRoundMatched);
        modelAndView.addObject("lottosRoundNotmatched", lottosRoundNotmatched);
        modelAndView.addObject("deliveryList", deliveryList);
        modelAndView.addObject("roundList", roundList);
        modelAndView.setViewName("delivery/list");
        return modelAndView;
    }
}
