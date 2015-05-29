package com.ericsson.cac.sprint.adapters;

import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by echasis on 23/01/2015.
 */
@Component
@Aspect
public class SubscriberManagementServiceLogging {
    private static Gson gson = new Gson();
    private Logger logger = LoggerFactory.getLogger(SubscriberManagementServiceLogging.class);

    @Before("execution(* com.ericsson.cac.sprint.adapters.SubscriberManagementProxyService.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String arguments = gson.toJson(joinPoint.getArgs());
        logger.info("going to call method {} of class {} with arguments {}", methodName, className, arguments);
    }

    @AfterReturning(
            pointcut = "execution(* com.ericsson.cac.sprint.adapters.SubscriberManagementProxyService.*(..))",
            returning= "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        logger.info("finished call to method {} of class {} with returned value {}", methodName, className, gson.toJson(result));
    }

    @AfterThrowing(
            pointcut = "execution(* com.ericsson.cac.sprint.adapters.SubscriberManagementProxyService.*(..))",
            throwing= "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        logger.info("Exception thrown by call to method {} of class {} and exception is ", methodName, className, error.fillInStackTrace());
    }
}
