package com.lottomatching.controller;

import com.lottomatching.domain.User;
import com.lottomatching.repository.UserRepository;
import com.lottomatching.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the username provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("signup");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("login");

        }
        return modelAndView;
    }

    @RequestMapping(value = "/editUser/{email}", method = RequestMethod.GET)
    public ModelAndView editUser( @PathVariable("email") String email) {
        User user = userRepository.findByEmail(email);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editUser");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(value = "/editUser", method = RequestMethod.POST)
    public ModelAndView editedUser(@Valid User user, @RequestParam("action") String action) {
        ModelAndView modelAndView = new ModelAndView();
        User userUpdate = userRepository.findByEmail(user.getEmail());
            if(action.equals("edit")) {
                userUpdate.setFullName(user.getFullName());
                userUpdate.setTel(user.getTel());
                userUpdate.setAddress(user.getAddress());
                userUpdate.setNote(user.getNote());
            }else if(action.equals("delete")) {
                userUpdate.setEnabled(false);
            }
        userService.editUser(userUpdate);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userAuth = userService.findUserByEmail(auth.getName());
        List<User> usersList = userService.findUsers();
        modelAndView.addObject("usersList", usersList);
        modelAndView.addObject("currentUser", userAuth);
        modelAndView.addObject("currentUserRoles", userAuth.getRoles());
        modelAndView.addObject("fullName", userAuth.getFullName());
        modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
        modelAndView.setViewName("dashboard");
        return modelAndView;
    }
}
