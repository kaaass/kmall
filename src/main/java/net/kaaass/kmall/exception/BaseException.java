package net.kaaass.kmall.exception;

import lombok.Getter;
import net.kaaass.kmall.util.StatusEnum;

/**
 * 通用错误类
 *
 * 记录状态码
 */
@Getter
public class BaseException extends Exception {
    StatusEnum status = StatusEnum.SUCCESS;

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
