package net.kaaass.kmall.dto;

import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class UserAddressDto {
    @Nullable
    private String id;

    private String area;

    private String detailAddress;

    private String mailCode;

    private String phone;

    private String name;

    private boolean defaultAddress;
}
