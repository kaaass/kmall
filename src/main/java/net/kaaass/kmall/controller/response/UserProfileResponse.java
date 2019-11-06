package net.kaaass.kmall.controller.response;

import lombok.Data;
import net.kaaass.kmall.dto.UserInfoDto;
import net.kaaass.kmall.vo.UserOrderCountVo;

@Data
public class UserProfileResponse {

    private UserInfoDto info;

    private UserOrderCountVo orderCount;
}
