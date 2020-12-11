package com.lottomatching.controller;

import com.lottomatching.domain.News;
import com.lottomatching.repository.NewsRepository;
import com.lottomatching.service.CustomUserDetailsService;
import com.lottomatching.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NewsController {

    @Autowired
    private NewsService NewsService;

    @Autowired
    private NewsRepository newsRepository;


    @RequestMapping(value = "/admin/news}", method = RequestMethod.GET)
    public ModelAndView newsHome() {
        News news = NewsService.findNewsById(new Long(1));

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("news", news);
        modelAndView.setViewName("admin/news");
        return modelAndView;
    }

}
