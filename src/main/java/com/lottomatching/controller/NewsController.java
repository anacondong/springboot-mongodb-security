package com.lottomatching.controller;

import com.lottomatching.domain.News;
import com.lottomatching.domain.User;
import com.lottomatching.repository.NewsRepository;
import com.lottomatching.service.CustomUserDetailsService;
import com.lottomatching.service.NewsService;
import com.lottomatching.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NewsController {

    @Autowired
    private NewsService NewsService;

    @Autowired
    private CustomUserDetailsService userService;

    @RequestMapping(value = "/admin/news", method = RequestMethod.GET)
    public ModelAndView newsHome() {
        News news = NewsService.findNewsById(new Long(1));
        ModelAndView modelAndView = new ModelAndView();

        Utils.setCurrentUser(userService, modelAndView);

        modelAndView.addObject("news", news);
        modelAndView.setViewName("admin/adminNews");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/news", method = RequestMethod.POST)
    public ModelAndView newsEdit(@RequestParam("msg") String msg) {
        News news = NewsService.findNewsById(new Long(1));
        news.setMsg(msg);
        NewsService.save(news);
        ModelAndView modelAndView = new ModelAndView();

        Utils.setCurrentUser(userService, modelAndView);

        modelAndView.addObject("news", news);
        modelAndView.addObject("message", "สำเร็จ");
        modelAndView.setViewName("admin/adminNews");
        return modelAndView;
    }

}
