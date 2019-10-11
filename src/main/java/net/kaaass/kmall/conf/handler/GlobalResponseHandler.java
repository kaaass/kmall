package net.kaaass.kmall.conf.handler;

import lombok.extern.slf4j.Slf4j;
import net.kaaass.kmall.controller.response.GlobalResponse;
import net.kaaass.kmall.exception.BaseException;
import net.kaaass.kmall.util.StatusEnum;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        final var returnType = methodParameter.getParameterType();
        return !returnType.isAssignableFrom(GlobalResponse.class)
                && !returnType.equals(ResponseEntity.class);
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        final var returnType = methodParameter.getParameterType();
        if (returnType.equals(Void.TYPE)) {
            return GlobalResponse.success(null);
        }
        if (!mediaType.includes(MediaType.APPLICATION_JSON)) {
            return o;
        }
        return GlobalResponse.success(o);
    }

    @ResponseBody
    @ExceptionHandler({BaseException.class})
    public <T> ResponseEntity<GlobalResponse<T>> handleBaseException(BaseException e) {
        log.warn("发生异常", e);
        return new ResponseEntity<>(GlobalResponse.fail(e.getStatus(), e.getMessage()), e.getStatus().toHttpStatus());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public <T> GlobalResponse<T> handleException(Exception e) {
        log.error("发生未知异常", e);
        // 不应该暴露栈信息给Rest接口
        return GlobalResponse.fail(StatusEnum.INTERNAL_ERROR, e.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Throwable.class})
    public <T> GlobalResponse<T> handleException(Throwable e) {
        log.error("发生Throwable错误", e);
        // 不应该暴露栈信息给Rest接口
        return GlobalResponse.fail(StatusEnum.INTERNAL_ERROR, e.getMessage());
    }
}
