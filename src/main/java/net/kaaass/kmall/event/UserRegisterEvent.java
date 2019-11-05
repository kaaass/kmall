package net.kaaass.kmall.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.kaaass.kmall.dto.UserAuthDto;
import net.kaaass.kmall.eventhandle.Event;

/**
 * 用户注册事件
 *
 * 值修改将会被忽略
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRegisterEvent extends Event {

    UserAuthDto userAuth;
}
