package net.kaaass.kmall.exception;

import lombok.Getter;
import net.kaaass.kmall.exception.BaseException;
import net.kaaass.kmall.util.StatusEnum;

@Getter
public class NotFoundException extends BaseException {
    StatusEnum status = StatusEnum.NOT_FOUND;

    public NotFoundException(String message) {
        super(message);
    }
}
