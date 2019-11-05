package net.kaaass.kmall.service;

import net.kaaass.kmall.controller.response.LoginResponse;
import net.kaaass.kmall.dto.AuthTokenDto;
import net.kaaass.kmall.dto.UserAuthDto;

import java.util.Optional;

public interface AuthService {
    /**
     * 用户注册
     * @param userToAdd
     * @return
     */
    Optional<UserAuthDto> register(UserAuthDto userToAdd);

    /**
     * 用户登录
     * @param phone
     * @param password
     * @return
     */
    Optional<LoginResponse> login(String phone, String password);

    /**
     * 令牌刷新
     * @param oldToken
     * @return
     */
    Optional<AuthTokenDto> refresh(String oldToken);

    /**
     * 短信验证
     * @param user
     * @return
     */
    boolean validate(UserAuthDto user);
}