package com.lottomatching.controller;


import com.lottomatching.domain.News;
import com.lottomatching.domain.Round;
import com.lottomatching.domain.User;
import com.lottomatching.service.CustomUserDetailsService;
import com.lottomatching.service.NewsService;
import com.lottomatching.service.RoundService;
import com.lottomatching.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class RoundController {

    @Autowired
    private RoundService roundService;

    @Autowired
    private CustomUserDetailsService userService;

    @RequestMapping(value = "/admin/round", method = RequestMethod.GET)
    public ModelAndView roundHome() {
        List<Round> roundList = roundService.findAll();
        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("currentUser", currentUser);
        modelAndView.addObject("currentUserRoles", Utils.getCurrentUserRole(currentUser.getRoles()));
        modelAndView.addObject("fullName", currentUser.getFullName());

        modelAndView.addObject("roundList", roundList);
        modelAndView.setViewName("round/list");
        return modelAndView;
    }
}
