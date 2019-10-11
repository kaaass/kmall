package net.kaaass.kmall.conf.aspect;

import lombok.extern.slf4j.Slf4j;
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
        Object data = proceedingJoinPoint.proceed();
        // 请求处理后
        return data;
    }
}
