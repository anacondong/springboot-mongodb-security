package com.lottomatching.utils;

import com.lottomatching.domain.Lotto;
import com.lottomatching.domain.Role;
import com.lottomatching.domain.User;
import com.lottomatching.service.CustomUserDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class Utils {


    public static List<Lotto> getMatchedSystemLotto(List<Lotto> systemLotto) {

        // 0) not same barcode 1) same round >> 2) same lotto number, 3) 1-5 group
        Map groupMap = Utils.getGroupMap();
        List<Lotto> matchedLotto = new ArrayList<Lotto>();
        for(Lotto ul: systemLotto){
            for(Lotto sl: systemLotto){
                if(!(ul.getBarcode().equals(sl.getBarcode()))) { // not same barcode
                    if (ul.getRound().equals(sl.getRound())) { // same round
                        if (ul.getNumber().equals(sl.getNumber())) { // same number
                            if (groupMap.get(ul.getGroup()).equals(groupMap.get(sl.getGroup()))) { // same in the group length
                                ul.setMatch(true);
                                matchedLotto.add(ul);
                            }
                        }
                    }
                }
            }
        }
        return matchedLotto;
    }

    public static List<Lotto> getMatchedUserLottoAndSystemLotto(List<Lotto> userLotto, List<Lotto> systemLotto) {

        // 0) not same barcode 1) same round >> 2) same lotto number, 3) 1-5 group
        Map groupMap = Utils.getGroupMap();
        List<Lotto> matchedLotto = new ArrayList<Lotto>();
        for(Lotto ul: userLotto){
            for(Lotto sl: systemLotto){
                if(!(ul.getBarcode().equals(sl.getBarcode()))) { // not same barcode
                    if (ul.getRound().equals(sl.getRound())) { // same round
                        if (ul.getNumber().equals(sl.getNumber())) { // same number
                            if (groupMap.get(ul.getGroup()).equals(groupMap.get(sl.getGroup()))) { // same in the group length
                                matchedLotto.add(ul);
                            }
                        }
                    }
                }
            }
        }

        //remove duplicate barcode
        Map<String, Lotto> matchedLottoMap = new HashMap<String, Lotto>();
        for(Lotto l : matchedLotto) {
            matchedLottoMap.put(l.getBarcode(), l);
        }
        matchedLotto.clear();
        for (Map.Entry<String, Lotto> entry : matchedLottoMap.entrySet()) {
            matchedLotto.add(entry.getValue());
        }
        return matchedLotto;
    }

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

    public static void setMatchedLottoToGroup(List<Lotto> matchedLotto, ModelAndView modelAndView){

        List<Lotto> r0 = new ArrayList<Lotto>();
        List<Lotto> r1 = new ArrayList<Lotto>();
        List<Lotto> r2 = new ArrayList<Lotto>();
        List<Lotto> r3 = new ArrayList<Lotto>();
        List<Lotto> r4 = new ArrayList<Lotto>();
        List<Lotto> r5 = new ArrayList<Lotto>();
        List<Lotto> r6 = new ArrayList<Lotto>();
        List<Lotto> r7 = new ArrayList<Lotto>();
        List<Lotto> r8 = new ArrayList<Lotto>();
        List<Lotto> r9 = new ArrayList<Lotto>();

        matchedLotto = matchedLotto.stream().sorted((o1, o2)->o1.getNumber().
                compareTo(o2.getNumber())).
                collect(Collectors.toList());

        for(Lotto l: matchedLotto) {
            String r = l.getNumber().substring(0,1);
            if("0".equals(r)) {r0.add(l);}
            else if("1".equals(r)) {r1.add(l);}
            else if("2".equals(r)) {r2.add(l);}
            else if("3".equals(r)) {r3.add(l);}
            else if("4".equals(r)) {r4.add(l);}
            else if("5".equals(r)) {r5.add(l);}
            else if("6".equals(r)) {r6.add(l);}
            else if("7".equals(r)) {r7.add(l);}
            else if("8".equals(r)) {r8.add(l);}
            else if("9".equals(r)) {r9.add(l);}
        }

        modelAndView.addObject("r0", r0);
        modelAndView.addObject("r1", r1);
        modelAndView.addObject("r2", r2);
        modelAndView.addObject("r3", r3);
        modelAndView.addObject("r4", r4);
        modelAndView.addObject("r5", r5);
        modelAndView.addObject("r6", r6);
        modelAndView.addObject("r7", r7);
        modelAndView.addObject("r8", r8);
        modelAndView.addObject("r9", r9);

    }

    public static Map getGroupMap(){

        Map groupMap = new HashMap();

        groupMap.put("01","g1");
        groupMap.put("02","g1");
        groupMap.put("03","g1");
        groupMap.put("04","g1");
        groupMap.put("05","g1");

        groupMap.put("06","g2");
        groupMap.put("07","g2");
        groupMap.put("08","g2");
        groupMap.put("09","g2");
        groupMap.put("10","g2");

        groupMap.put("11","g3");
        groupMap.put("12","g3");
        groupMap.put("13","g3");
        groupMap.put("14","g3");
        groupMap.put("15","g3");

        groupMap.put("16","g4");
        groupMap.put("17","g4");
        groupMap.put("18","g4");
        groupMap.put("19","g4");
        groupMap.put("20","g4");

        groupMap.put("21","g5");
        groupMap.put("22","g5");
        groupMap.put("23","g5");
        groupMap.put("24","g5");
        groupMap.put("25","g5");

        groupMap.put("26","g6");
        groupMap.put("27","g6");
        groupMap.put("28","g6");
        groupMap.put("29","g6");
        groupMap.put("30","g6");

        groupMap.put("31","g7");
        groupMap.put("32","g7");
        groupMap.put("33","g7");
        groupMap.put("34","g7");
        groupMap.put("35","g7");

        groupMap.put("36","g8");
        groupMap.put("37","g8");
        groupMap.put("38","g8");
        groupMap.put("39","g8");
        groupMap.put("40","g8");

        groupMap.put("41","g9");
        groupMap.put("42","g9");
        groupMap.put("43","g9");
        groupMap.put("44","g9");
        groupMap.put("45","g9");

        groupMap.put("46","g10");
        groupMap.put("47","g10");
        groupMap.put("48","g10");
        groupMap.put("49","g10");
        groupMap.put("50","g10");

        groupMap.put("51","g11");
        groupMap.put("52","g11");
        groupMap.put("53","g11");
        groupMap.put("54","g11");
        groupMap.put("55","g11");

        groupMap.put("56","g12");
        groupMap.put("57","g12");
        groupMap.put("58","g12");
        groupMap.put("59","g12");
        groupMap.put("60","g12");

        groupMap.put("61","g13");
        groupMap.put("62","g13");
        groupMap.put("63","g13");
        groupMap.put("64","g13");
        groupMap.put("65","g13");

        groupMap.put("66","g14");
        groupMap.put("67","g14");
        groupMap.put("68","g14");
        groupMap.put("69","g14");
        groupMap.put("70","g14");

        groupMap.put("71","g15");
        groupMap.put("72","g15");
        groupMap.put("73","g15");
        groupMap.put("74","g15");
        groupMap.put("75","g15");

        groupMap.put("76","g16");
        groupMap.put("77","g16");
        groupMap.put("78","g16");
        groupMap.put("79","g16");
        groupMap.put("80","g16");

        groupMap.put("81","g17");
        groupMap.put("82","g17");
        groupMap.put("83","g17");
        groupMap.put("84","g17");
        groupMap.put("85","g17");

        groupMap.put("86","g18");
        groupMap.put("87","g18");
        groupMap.put("88","g18");
        groupMap.put("89","g18");
        groupMap.put("90","g18");

        groupMap.put("91","g19");
        groupMap.put("92","g19");
        groupMap.put("93","g19");
        groupMap.put("94","g19");
        groupMap.put("95","g19");

        groupMap.put("96","g20");
        groupMap.put("97","g20");
        groupMap.put("98","g20");
        groupMap.put("99","g20");
        groupMap.put("00","g20");

        return groupMap;
    }

}
