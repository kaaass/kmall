package net.kaaass.kmall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class AuthTokenDto {
    String token;
    Date expired;
}
