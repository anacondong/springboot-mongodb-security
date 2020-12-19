package com.lottomatching.controller;


import com.lottomatching.domain.Lotto;
import com.lottomatching.domain.Round;
import com.lottomatching.domain.User;
import com.lottomatching.service.CustomUserDetailsService;
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
import java.util.List;

@Controller
public class MatchController {

    @Autowired
    private LottoService lottoService;

    @Autowired
    private CustomUserDetailsService userService;

    @Autowired
    private RoundService roundService;

    @RequestMapping(value = "/user/match", method = RequestMethod.GET)
    public ModelAndView matchHome() {
        ModelAndView modelAndView = new ModelAndView();

        Utils.setCurrentUser(userService, modelAndView);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByEmail(auth.getName());
        List<Round> roundList = roundService.findByStatus("process");
        List<Lotto> lottos = new ArrayList<Lotto>();
        List<Lotto> lottosAllMatch = new ArrayList<Lotto>();
        for(Round round: roundList){
            lottos.addAll(lottoService.findByUserAndRoundAndEnabledOrderByIdDesc(currentUser,round.getNumber(), true)); // user lotto this round
            lottosAllMatch.addAll(lottoService.findByRoundAndEnabledOrderByIdDesc(round.getNumber(), true)); // all lotto in this round
        }

        lottos.stream().forEach(l -> System.out.println("<<< lottos >>> barcode: "+l.getBarcode()+" number: "+l.getNumber()+" group: "+l.getGroup()+" round: "+l.getRound()));
        lottosAllMatch.stream().forEach(l -> System.out.println("<<< lottosAllMatch >>> barcode: "+l.getBarcode()+" number: "+l.getNumber()+" group: "+l.getGroup()+" round: "+l.getRound()));
        // todo matching logic



        modelAndView.addObject("lottos", lottos);
        modelAndView.addObject("roundList", roundList);
        modelAndView.addObject("lottosAllMatch", lottosAllMatch);
        modelAndView.setViewName("match/list");
        return modelAndView;
    }
}
