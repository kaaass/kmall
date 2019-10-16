package net.kaaass.kmall.controller;

import net.kaaass.kmall.dao.entity.UserAuthEntity;
import net.kaaass.kmall.dao.repository.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class BaseController {
    @Autowired
    private UserAuthRepository authRepository;

    private static UserDetails getAuthUserDetail() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        return (UserDetails) auth.getPrincipal();
    }

    /**
     * 获得鉴权用户的uid
     * @return
     */
    protected static String getUid() {
        return getAuthUserDetail().getUsername();
    }

    /**
     * 获得鉴权用户的 auth entity
     * @return
     */
    protected UserAuthEntity getAuthEntity() {
        return authRepository.findById(getUid()).orElseThrow();
    }
}
