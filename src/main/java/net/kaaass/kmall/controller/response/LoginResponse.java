package net.kaaass.kmall.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.kaaass.kmall.dto.AuthTokenDto;

@Data
@AllArgsConstructor
public class LoginResponse {

    private AuthTokenDto authToken;

    private String phone;

    private boolean admin;
}
