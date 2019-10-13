package net.kaaass.kmall.dto;

import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class UserAuthDto {

    @Nullable
    private String id;

    private String phone;

    private String password;
}