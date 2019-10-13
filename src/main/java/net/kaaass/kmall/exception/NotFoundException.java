package net.kaaass.kmall.exception;

import lombok.Getter;
import net.kaaass.kmall.util.StatusEnum;

/**
 * 未找到错误
 */
@Getter
public class NotFoundException extends BaseException {
    StatusEnum status = StatusEnum.NOT_FOUND;

    public NotFoundException(String message) {
        super(message);
    }
}
