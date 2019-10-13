package net.kaaass.kmall.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class BaseController {
    private static UserDetails getUserDetail() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        return (UserDetails) auth.getPrincipal();
    }

    /**
     * 获得鉴权用户的uid
     * @return
     */
    protected static String getUid() {
        return getUserDetail().getUsername();
    }
}
