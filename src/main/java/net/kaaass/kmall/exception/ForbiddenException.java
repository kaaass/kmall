package net.kaaass.kmall.exception;

import lombok.Getter;
import net.kaaass.kmall.util.StatusEnum;

/**
 * 无权请求错误
 */
@Getter
public class ForbiddenException extends BaseException {
    StatusEnum status = StatusEnum.FORBIDDEN;

    public ForbiddenException(String message) {
        super(message);
    }
}