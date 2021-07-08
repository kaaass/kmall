package net.kaaass.kmall.controller.request;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserAddressRequest {

    @Size(
            message = "地区必须在3~20字之间",
            min = 3,
            max = 20
    )
    private String area;

    @Size(
            message = "详细地址必须在5~20字之间",
            min = 5,
            max = 20
    )
    private String detailAddress;

    @Digits(
            message = "邮编必须为6位数字",
            integer = 6,
            fraction = 0
    )
    private String mailCode;


    @Pattern(
            message = "手机号格式错误",
            regexp = "^\\d{11}$"
    )
    private String phone;

    @Size(
            message = "姓名必须在2~20字之间",
            min = 2,
            max = 20
    )
    private String name;
}
