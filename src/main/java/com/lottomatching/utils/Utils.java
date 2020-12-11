package com.lottomatching.utils;

import com.lottomatching.domain.Role;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Utils {

    public static String getCurrentUserRole(Set<Role> roles) {
        List<Role> role = roles.stream()
                .filter(c -> c.getRole().equals("ADMIN"))
                .collect(Collectors.toList());
        return (role.isEmpty())?"USER" : "ADMIN";
    }
}
