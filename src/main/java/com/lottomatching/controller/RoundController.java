package com.lottomatching.controller;


import com.lottomatching.domain.*;
import com.lottomatching.service.*;
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

import java.util.ArrayList;
import java.util.List;

@Controller
public class RoundController {

    @Autowired
    private RoundService roundService;

    @Autowired
    private CustomUserDetailsService userService;

    @Autowired
    private LottoService lottoService;

    @Autowired
    private DeliveryService deliveryService;

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
        int count = 0;
        round.setName(name);
        round.setStatus(status);

        List<Lotto> lottoList = lottoService.findByRound(number);
        List<Delivery> deliveryList = deliveryService.findByRound(number);
        if(status.equals("open")){
            lottoList.stream().forEach(lotto -> lotto.setEnabled(true));
            count = lottoService.saveAll(lottoList);

            deliveryList.stream().forEach(delivery -> delivery.setEnabled(true));
            int updatedDeliver = deliveryService.saveAll(deliveryList);
//            System.out.println("updated delivery: "+ updatedDeliver);

        }

        if(status.equals("close")){
            lottoList.stream().forEach(lotto -> lotto.setEnabled(false));
            count = lottoService.saveAll(lottoList);

            deliveryList.stream().forEach(delivery -> delivery.setEnabled(false));
            int updatedDeliver = deliveryService.saveAll(deliveryList);
//            System.out.println("updated delivery: "+ updatedDeliver);
        }

        if(status.equals("process")){
            // process match lotto
            lottoList.stream().forEach(lotto -> lotto.setEnabled(true));
            lottoService.saveAll(lottoList);
            List<Lotto> matchedLotto = Utils.getMatchedSystemLotto(lottoList);
            count = lottoService.saveAll(matchedLotto);

            // process summary match delivery lotto
            List<User> usersList = userService.findUsers();
            for(User user: usersList) {
                if (!(user.getEmail().equals("admin"))){ // exclude admin

                    List<Delivery> userDeliveryList = deliveryService.findByUserAndRound(user, round.getNumber());
                    int lottoUserRoundAllList = lottoService.findByUserAndRoundOrderByIdDesc(user, round.getNumber()).size();
                    int lottoUserRoundMatchedList = lottoService.findByUserAndRoundAndMatch(user, round.getNumber(), true).size();
                    int lottoUserRoundNotMatchedList = lottoService.findByUserAndRoundAndMatch(user, round.getNumber(), false).size();
                    int lottoUserRoundReceivedList = lottoService.findByUserAndRoundAndReceived(user, round.getNumber(), true).size();
                    Delivery delivery = new Delivery();
                    if (userDeliveryList.isEmpty()) {
                        // insert summary delivery
                        delivery = new Delivery();
                        delivery.setUser(user);
                        delivery.setEnabled(true);
                        delivery.setRound(round.getNumber());
                        delivery.setStatus("Pending");
                        delivery.setLottoList(lottoUserRoundAllList); // all lotto in this round
                        delivery.setMatched(lottoUserRoundMatchedList); // user match this round
                        delivery.setNotMatched(lottoUserRoundNotMatchedList);// user not match this round
                        delivery.setReceived(lottoUserRoundReceivedList);

                    } else {
                        // update summary delivery
                        delivery = userDeliveryList.get(0);
                        delivery.setUser(user);
                        delivery.setEnabled(true);
                        delivery.setRound(round.getNumber());
                        delivery.setStatus("Pending");
                        delivery.setLottoList(lottoUserRoundAllList); // all lotto in this round
                        delivery.setMatched(lottoUserRoundMatchedList); // user match this round
                        delivery.setNotMatched(lottoUserRoundNotMatchedList);// user not match this round
                        delivery.setReceived(lottoUserRoundReceivedList);
                    }
                    deliveryService.save(delivery);
                }
            }
        }

        boolean result = roundService.save(round);
        if(result){
            modelAndView.addObject("message", "ดำเนินการสำเร็จจำนวน :"+count);
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
