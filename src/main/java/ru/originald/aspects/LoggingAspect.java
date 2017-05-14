package ru.originald.aspects;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * Created by redin on 5/12/17.
 */
@Component
@Aspect
public class LoggingAspect {

    @Pointcut("within(ru.originald.service.*)")
    public void servicePoint(){}

    @Pointcut("within(ru.originald.controller.*)")
    public void controllerPoint(){}

    @Around("servicePoint() || controllerPoint()")
    public Object log(ProceedingJoinPoint pjp) throws Throwable {
        Logger logger = LoggerFactory.getLogger(pjp.getSignature().getDeclaringType());

        logger.debug("Calling method:{}({})",pjp.getSignature().getName(),StringUtils.join(pjp.getArgs(),", "));

        Object result;

        final StopWatch watch = new StopWatch();
        watch.start();

        result = pjp.proceed();

        watch.stop();

        logger.debug("Returning method: {}, speed of execution(in mills): {}",
                pjp.getSignature().getName(), watch.getTotalTimeMillis());

        return result;

    }
}
