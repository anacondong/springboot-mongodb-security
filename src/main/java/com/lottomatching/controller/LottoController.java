package com.lottomatching.controller;

import com.lottomatching.domain.Lotto;
import com.lottomatching.domain.News;
import com.lottomatching.domain.User;
import com.lottomatching.service.CustomUserDetailsService;
import com.lottomatching.service.LottoService;
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

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
public class LottoController {

    @Autowired
    private LottoService lottoService;

    @Autowired
    private CustomUserDetailsService userService;

    @RequestMapping(value = "/user/lotto", method = RequestMethod.GET)
    public ModelAndView lottoList() {
        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("currentUser", currentUser);
        modelAndView.addObject("currentUserRoles", Utils.getCurrentUserRole(currentUser.getRoles()));
        modelAndView.addObject("fullName", currentUser.getFullName());

        List<Lotto> lottos = lottoService.findByUserOrderByIdDesc(currentUser);
        modelAndView.addObject("lottos", lottos);
        modelAndView.setViewName("lotto/list");
        return modelAndView;
    }

    @RequestMapping(value = "/user/lotto/add", method = RequestMethod.POST)
    public ModelAndView lottoAdd(@RequestParam("barcode") String barcode) {

//        System.out.println("barcode:"+ barcode);
//        List<String> barcodeList = Arrays.asList(barcode.split("\\r?\\n"));
//        for(String b : barcodeList){
//            System.out.println("========================================");
//            System.out.println("barcode: "+b);
//            System.out.println("round: "+b.substring(2,4));
//            System.out.println("group: "+b.substring(4,6));
//            System.out.println("number: "+b.substring(6,10));
//            System.out.println("========================================");
//        }


        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("currentUser", currentUser);
        modelAndView.addObject("currentUserRoles", Utils.getCurrentUserRole(currentUser.getRoles()));
        modelAndView.addObject("fullName", currentUser.getFullName());
        int sendCount = 0;
        try {
            List<String> barcodeList = Arrays.asList(barcode.split("\\r?\\n"));

            for (String b : barcodeList) {
                Lotto lotto = new Lotto();
                lotto.setBarcode(b);
                lotto.setRound(b.substring(2, 4));
                lotto.setGroup(b.substring(4, 6));
                lotto.setNumber(b.substring(6, 10));
                lotto.setDate(new Date());
                lotto.setEnabled(true);
                lotto.setUser(currentUser);
                sendCount = sendCount + lottoService.save(lotto);
            }
        } catch (Exception e) {
            modelAndView.addObject("messageError", "ดำเนินการไม่สำเร็จ กรุณาลองไหม่");
        }
        List<Lotto> lottos = lottoService.findByUserOrderByIdDesc(currentUser);
        modelAndView.addObject("message", "จำนวนที่ส่งได้: " + sendCount);
        modelAndView.addObject("lottos", lottos);
        modelAndView.setViewName("lotto/list");
        return modelAndView;
    }

}
