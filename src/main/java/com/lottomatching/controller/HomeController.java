/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lottomatching.controller;

import com.lottomatching.domain.Role;
import com.lottomatching.domain.User;
import com.lottomatching.repository.UserRepository;
import com.lottomatching.service.CustomUserDetailsService;
import javax.validation.Valid;

import com.lottomatching.service.NewsService;
import com.lottomatching.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Set;

@Controller
public class HomeController {

    @Autowired
    private CustomUserDetailsService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NewsService newsService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

   @RequestMapping(value = "/admin/dashboard", method = RequestMethod.GET)
    public ModelAndView adminDashboard() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("currentUser", user);
        modelAndView.addObject("currentUserRoles", Utils.getCurrentUserRole(user.getRoles()));
        modelAndView.addObject("fullName", user.getFullName());
        modelAndView.setViewName("admin/dashboard");
        return modelAndView;
    }


    @RequestMapping(value = "/user/dashboard", method = RequestMethod.GET)
    public ModelAndView userDashboard() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("currentUser", user);
        modelAndView.addObject("currentUserRoles", Utils.getCurrentUserRole(user.getRoles()));
        modelAndView.addObject("fullName", user.getFullName());
        modelAndView.addObject("news", newsService.findNewsById(new Long(1)).getMsg());
        modelAndView.setViewName("dashboard");
        return modelAndView;
    }
    
    @RequestMapping(value = {"/","/home"}, method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @RequestMapping(value = "/changePassword/{email}", method = RequestMethod.GET)
    public ModelAndView changePassword( @PathVariable("email") String email) {
        User user = userRepository.findByEmail(email);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("changePassword");
        modelAndView.addObject("user", user);
        return modelAndView;
    }


}


//    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//    User user = userService.findUserByEmail(auth.getName());
//    modelAndView.addObject("currentUser", user);