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
import java.util.Map;
import java.util.stream.Collectors;

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

        // start user matching logic
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByEmail(auth.getName());
        List<Round> roundList = roundService.findByStatus("process");
        List<Lotto> userLotto = new ArrayList<Lotto>();
        List<Lotto> systemLotto = new ArrayList<Lotto>();
        for(Round round: roundList){
            userLotto.addAll(lottoService.findByUserAndRoundAndEnabledOrderByIdDesc(currentUser,round.getNumber(), true));
            systemLotto.addAll(lottoService.findByRoundAndEnabledOrderByIdDesc(round.getNumber(), true));
        }

        // matching logic
        List<Lotto> matchedLotto = new ArrayList<Lotto>();
        matchedLotto.addAll(Utils.getMatchedUserLottoAndSystemLotto(userLotto, systemLotto));

        // end user matching logic

        Utils.setMatchedLottoToGroup(matchedLotto, modelAndView);

        modelAndView.addObject("userLotto", userLotto);
        modelAndView.addObject("matchedLotto", matchedLotto);
        modelAndView.addObject("roundList", roundList);

        modelAndView.setViewName("match/list");
        return modelAndView;
    }
}
