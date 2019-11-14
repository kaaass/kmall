package net.kaaass.kmall.controller;

import net.kaaass.kmall.dto.UserInfoDto;
import net.kaaass.kmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    List<UserInfoDto> getAllUser() {
        return userService.getAllUser();
    }
}
