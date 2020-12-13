package com.lottomatching.controller;

import com.lottomatching.domain.User;
import com.lottomatching.repository.UserRepository;
import com.lottomatching.service.CustomUserDetailsService;
import com.lottomatching.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailsService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @RequestMapping(value = "/admin/listUser", method = RequestMethod.GET)
    public ModelAndView listUser() {
        ModelAndView modelAndView = new ModelAndView();
        List<User> usersList = userService.findUsers();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("currentUser", currentUser);
        modelAndView.addObject("currentUserRoles", Utils.getCurrentUserRole(currentUser.getRoles()));
        modelAndView.addObject("fullName", currentUser.getFullName());

        modelAndView.addObject("usersList", usersList);
        modelAndView.setViewName("user/listUser");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/addUser", method = RequestMethod.GET)
    public ModelAndView addUser() {
        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("currentUser", currentUser);
        modelAndView.addObject("currentUserRoles", Utils.getCurrentUserRole(currentUser.getRoles()));
        modelAndView.addObject("fullName", currentUser.getFullName());


        User us = new User();
        modelAndView.addObject("user", us);
        modelAndView.setViewName("user/addUser");
        return modelAndView;
    }


    @RequestMapping(value = "/admin/addUser", method = RequestMethod.POST)
    public ModelAndView addUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the username provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("messageError", "ดำเนินการไม่สำเร็จ");
            modelAndView.setViewName("user/listUser");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("message", "ดำเนินการสำเร็จ");
            List<User> usersList = userService.findUsers();
            modelAndView.addObject("usersList", usersList);
            modelAndView.setViewName("user/listUser");

        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("currentUser", currentUser);
        modelAndView.addObject("currentUserRoles", Utils.getCurrentUserRole(currentUser.getRoles()));
        modelAndView.addObject("fullName", currentUser.getFullName());

        return modelAndView;
    }

    @RequestMapping(value = "/admin/editUser/{email}", method = RequestMethod.GET)
    public ModelAndView editUser(@PathVariable("email") String email) {
        User user = userRepository.findByEmail(email);
        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("currentUser", currentUser);
        modelAndView.addObject("currentUserRoles", Utils.getCurrentUserRole(currentUser.getRoles()));
        modelAndView.addObject("fullName", currentUser.getFullName());

        modelAndView.setViewName("user/editUser");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(value = "/admin/editUser", method = RequestMethod.POST)
    public ModelAndView editedUser(@Valid User user, @RequestParam("action") String action) {
        ModelAndView modelAndView = new ModelAndView();

        User userUpdate = userRepository.findByEmail(user.getEmail());
        if (action.equals("edit")) {
            userUpdate.setFullName(user.getFullName());
            userUpdate.setTel(user.getTel());
            userUpdate.setAddress(user.getAddress());
            userUpdate.setNote(user.getNote());
        } else if (action.equals("delete")) {
            userUpdate.setEnabled(false);
        } else if (action.equals("setPassword")) {
            userUpdate.setPassword("$2a$10$KXcCwpQzwA6DRY0e2Du2duju7b7hxDBljYi9tGwiLUFT0DeLnecQu");
        }
        userService.editUser(userUpdate);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("currentUser", currentUser);
        modelAndView.addObject("currentUserRoles", Utils.getCurrentUserRole(currentUser.getRoles()));
        modelAndView.addObject("fullName", currentUser.getFullName());

        List<User> usersList = userService.findUsers();
        modelAndView.addObject("usersList", usersList);
        modelAndView.addObject("message", "ดำเนินการสำเร็จ");
        modelAndView.setViewName("user/listUser");
        return modelAndView;
    }


    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public ModelAndView changePassword(@RequestParam("email") String email, @RequestParam("password") String password,
                                       @RequestParam("newPassword") String newPassword,
                                       @RequestParam("confirmPassword") String confirmPassword) {
        ModelAndView modelAndView = new ModelAndView();
        User userUpdate = userRepository.findByEmail(email);

        if ((bCryptPasswordEncoder.matches(password, userUpdate.getPassword())) && (newPassword.equals(confirmPassword))) {
            userUpdate.setPassword(bCryptPasswordEncoder.encode(newPassword));
            userService.editUser(userUpdate);
            modelAndView.addObject("message", "ดำเนินการสำเร็จ");

        } else {
            modelAndView.addObject("messageError", "ดำเนินการไม่สำเร็จ");
        }

        User user = userRepository.findByEmail(email);
        modelAndView.setViewName("changePassword");
        modelAndView.addObject("user", user);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("currentUser", currentUser);
        modelAndView.addObject("currentUserRoles", Utils.getCurrentUserRole(currentUser.getRoles()));
        modelAndView.addObject("fullName", currentUser.getFullName());

        return modelAndView;
    }

}
