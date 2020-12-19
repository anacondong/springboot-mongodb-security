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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class RoundController {

    @Autowired
    private RoundService roundService;

    @Autowired
    private CustomUserDetailsService userService;

    @Autowired
    private LottoService lottoService;

    @RequestMapping(value = "/admin/round", method = RequestMethod.GET)
    public ModelAndView roundHome() {
        List<Round> roundList = roundService.findAll();
        ModelAndView modelAndView = new ModelAndView();

        Utils.setCurrentUser(userService, modelAndView);

        modelAndView.addObject("roundList", roundList);
        modelAndView.setViewName("round/list");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/editRound/{number}", method = RequestMethod.GET)
    public ModelAndView editRoundGet(@PathVariable("number") String number) {
        Round round = roundService.findByNumber(number);
        ModelAndView modelAndView = new ModelAndView();

        Utils.setCurrentUser(userService, modelAndView);

        modelAndView.addObject("round", round);
        modelAndView.setViewName("round/edit");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/editRound", method = RequestMethod.POST)
    public ModelAndView editRoundPost(@RequestParam("number") String number,
                                      @RequestParam("name") String name,
                                      @RequestParam("status") String status) {
        Round round = roundService.findByNumber(number);
        ModelAndView modelAndView = new ModelAndView();

        round.setName(name);
        round.setStatus(status);

        List<Lotto> lottoList = lottoService.findByRound(number);
        if(status.equals("open")){
            lottoList.stream().forEach(lotto -> lotto.setEnabled(true));
            int count = lottoService.saveAll(lottoList);
            System.out.println("Updated lotto status:"+count);
        }

        if(status.equals("close")){
            lottoList.stream().forEach(lotto -> lotto.setEnabled(false));
            int count = lottoService.saveAll(lottoList);
            System.out.println("Updated lotto status:"+count);
        }

        if(status.equals("process")){
            // todo not sure for this event
        }

        boolean result = roundService.save(round);
        if(result){
            modelAndView.addObject("message", "ดำเนินการสำเร็จ");
        }else {
            modelAndView.addObject("messageError", "ดำเนินการไม่สำเร็จ");
        }

        round = roundService.findByNumber(number);

        Utils.setCurrentUser(userService, modelAndView);

        modelAndView.addObject("round", round);
        modelAndView.setViewName("round/edit");
        return modelAndView;
    }
}
