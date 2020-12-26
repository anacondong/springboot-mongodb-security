package com.lottomatching.controller;

import com.lottomatching.domain.Delivery;
import com.lottomatching.domain.Lotto;
import com.lottomatching.domain.Round;
import com.lottomatching.domain.User;
import com.lottomatching.service.CustomUserDetailsService;
import com.lottomatching.service.DeliveryService;
import com.lottomatching.service.LottoService;
import com.lottomatching.service.RoundService;
import com.lottomatching.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class LottoGroupController {

    @Autowired
    private LottoService lottoService;

    @Autowired
    private CustomUserDetailsService userService;

    @Autowired
    private RoundService roundService;

    @Autowired
    private DeliveryService deliveryService;


    @RequestMapping(value = "/admin/lottoGroup", method = RequestMethod.GET)
    public ModelAndView LottoGroupHome() {
        ModelAndView modelAndView = new ModelAndView();

        Utils.setCurrentUser(userService, modelAndView);

        List<Round> roundList = roundService.findByStatus("process");
        List<Lotto> matchedLotto = new ArrayList<Lotto>();
        List<Lotto> receivedLotto = new ArrayList<Lotto>();
        List<Lotto> notReceivedLotto = new ArrayList<Lotto>();
        for(Round round: roundList){
            matchedLotto.addAll(lottoService.findByRoundAndMatch(round.getNumber(), true));
        }

        matchedLotto = matchedLotto.stream().sorted(Comparator.comparing(Lotto::getNumber).thenComparing(Lotto::getGroup)).collect(Collectors.toList());
        receivedLotto = matchedLotto.stream().filter(lotto -> (lotto.isReceived()== true)).collect(Collectors.toList());
        notReceivedLotto = matchedLotto.stream().filter(lotto -> (lotto.isReceived() == false)).collect(Collectors.toList());

        Map<String,List<Lotto>> lottoMap = new HashMap<String,List<Lotto>>();
        for(Lotto l: matchedLotto) {
            lottoMap.put(l.getNumber(),new ArrayList<Lotto>());
        }
        for (Map.Entry map : lottoMap.entrySet()) {
            List<Lotto> lottoArrayList = new ArrayList<Lotto>();
            for(Lotto l: matchedLotto) {
                if(map.getKey().equals(l.getNumber())){
                    lottoArrayList.add(l);
                    map.setValue(lottoArrayList);
                }

            }
        }
        for (Map.Entry map : lottoMap.entrySet()) {
            List<Lotto> mapValue =  (List<Lotto>)map.getValue();
        }

        // count sum for each type
        int s2 = 0;int s3 = 0;int s4 = 0;int s5 = 0;

        for (Map.Entry map : lottoMap.entrySet()) {
            List<Lotto> mapValue =  (List<Lotto>)map.getValue();
            if(mapValue.size() == 2){ s2 = s2+1;}
            else if(mapValue.size() == 3){s3 = s3+1;}
            else if(mapValue.size() == 4){s4 = s4+1;}
            else if(mapValue.size() == 5){s5 = s5+1;}
        }


        modelAndView.addObject("s2", s2);
        modelAndView.addObject("s3", s3);
        modelAndView.addObject("s4", s4);
        modelAndView.addObject("s5", s5);
        modelAndView.addObject("lottoMap", lottoMap);
        modelAndView.addObject("receivedLotto", receivedLotto);
        modelAndView.addObject("notReceivedLotto", notReceivedLotto);
        modelAndView.addObject("matchedLotto", matchedLotto);
        modelAndView.addObject("roundList", roundList);

        modelAndView.setViewName("lottogroup/list");
        return modelAndView;
    }
}

