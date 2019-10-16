package net.kaaass.kmall.controller;

import net.kaaass.kmall.vo.UserAuthVo;
import net.kaaass.kmall.dto.AuthTokenDto;
import net.kaaass.kmall.dto.UserAuthDto;
import net.kaaass.kmall.exception.BadRequestException;
import net.kaaass.kmall.mapper.UserMapper;
import net.kaaass.kmall.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController {
    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public AuthTokenDto createAuthenticationToken(
            @RequestParam String phone, @RequestParam String password) throws BadRequestException {
        // TODO 检查输入
        return authService.login(phone, password)
                .orElseThrow(() -> new BadRequestException("用户名或密码错误！"));
    }

    @GetMapping("/refresh")
    @PreAuthorize("authenticated")
    public AuthTokenDto refreshAndGetAuthenticationToken(HttpServletRequest request) throws BadRequestException {
        String token = request.getHeader(tokenHeader);
        return authService.refresh(token)
                .orElseThrow(() -> new BadRequestException("该Token无效！"));
    }

    @PostMapping("/register")
    public UserAuthVo register(@RequestBody UserAuthDto addedUser) throws BadRequestException {
        // TODO 检查输入，重复
        return authService.register(addedUser)
                .map(UserMapper.INSTANCE::userAuthDtoToVo)
                .orElseThrow(() -> new BadRequestException("该手机号已被注册！"));
    }
}
