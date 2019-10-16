package net.kaaass.kmall.dto;

import lombok.Data;
import net.kaaass.kmall.vo.UserAuthVo;

@Data
public class UserInfoDto {

    private UserAuthVo auth;

    private String wechat;

    private MediaDto avatar;
}
