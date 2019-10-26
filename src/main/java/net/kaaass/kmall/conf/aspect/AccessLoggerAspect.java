package net.kaaass.kmall.conf.aspect;

import lombok.extern.slf4j.Slf4j;
import net.kaaass.kmall.KmallApplication;
import net.kaaass.kmall.controller.response.GlobalResponse;
import net.kaaass.kmall.event.AfterControllerEvent;
import net.kaaass.kmall.event.BeforeControllerEvent;
import net.kaaass.kmall.exception.ForbiddenException;
import net.kaaass.kmall.util.StatusEnum;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * 访问日志切面
 */
@Slf4j
@Aspect
@Component
@Order(1) // 先于一切切面
@Transactional
public class AccessLoggerAspect {

    /**
     * 切点
     */
    @Pointcut("execution(public * net.kaaass.kmall.controller.*.*(..))")
    public void logMe() {
    }

    @Around("logMe()")
    public Object arround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 请求处理前
        var args = proceedingJoinPoint.getArgs();
        var staticPart = proceedingJoinPoint.getStaticPart();
        var cancel = KmallApplication.EVENT_BUS.post(new BeforeControllerEvent(args, staticPart));
        if (cancel) {
            throw new ForbiddenException("该访问被拦截！");
        }
        // 调用
        Object data = proceedingJoinPoint.proceed();
        // 请求处理后
        var event = new AfterControllerEvent(args, staticPart, data);
        KmallApplication.EVENT_BUS.post(event);
        data = event.getControllerResult();
        return data;
    }
}
