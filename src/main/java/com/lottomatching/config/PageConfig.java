/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lottomatching.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class PageConfig implements WebMvcConfigurer {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/login").setViewName("login");
//        registry.addViewController("/changePassword").setViewName("changePassword");
//        registry.addViewController("/user/dashboard").setViewName("dashboard");
//        registry.addViewController("/lotto/list").setViewName("lotto/list");
//        registry.addViewController("/admin/dashboard").setViewName("admin/dashboard");
//        registry.addViewController("/admin/editUser").setViewName("user/editUser");
//        registry.addViewController("/admin/listUser").setViewName("user/listUser");
//        registry.addViewController("/admin/news").setViewName("admin/adminNews");

    }

}
