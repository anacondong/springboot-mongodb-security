package com.lottomatching.controller;

import com.lottomatching.domain.Lotto;
import com.lottomatching.domain.News;
import com.lottomatching.domain.User;
import com.lottomatching.service.CustomUserDetailsService;
import com.lottomatching.service.LottoService;
import com.lottomatching.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class LottoController {

    @Autowired
    private LottoService lottoService;

    @Autowired
    private CustomUserDetailsService userService;

    @RequestMapping(value = "/user/lotto", method = RequestMethod.GET)
    public ModelAndView newsHome() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("currentUser", user);
        List<Lotto> lottos = lottoService.findByUser(user);
        modelAndView.addObject("lottos", lottos);
        modelAndView.setViewName("lotto/list");
        return modelAndView;
    }

}
