package com.lottomatching.utils;

import com.lottomatching.domain.Role;
import com.lottomatching.domain.User;
import com.lottomatching.service.CustomUserDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Utils {

    public static String getCurrentUserRole(Set<Role> roles) {
        List<Role> role = roles.stream()
                .filter(c -> c.getRole().equals("ADMIN"))
                .collect(Collectors.toList());
        return (role.isEmpty()) ? "USER" : "ADMIN";
    }

    public static void setCurrentUser(CustomUserDetailsService userService, ModelAndView modelAndView) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("currentUser", currentUser);
        modelAndView.addObject("currentUserRoles", Utils.getCurrentUserRole(currentUser.getRoles()));
        modelAndView.addObject("fullName", currentUser.getFullName());

    }
}
