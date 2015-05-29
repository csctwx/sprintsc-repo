package com.ericsson.cac.sprint.adapters;

import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class QueryUsageServiceLogging {
    private static Gson gson = new Gson();
    private Logger logger = LoggerFactory.getLogger(QueryUsageServiceLogging.class);

    @Before("execution(* com.ericsson.cac.sprint.adapters.QueryUsageProxyService.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String arguments = gson.toJson(joinPoint.getArgs());
        logger.info("going to call method {} of class {} with arguments {}", methodName, className, arguments);
    }

    @AfterReturning(
            pointcut = "execution(* com.ericsson.cac.sprint.adapters.QueryUsageProxyService.*(..))",
            returning= "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        logger.info("finished call to method {} of class {} with returned value {}", methodName, className, result.toString());
    }

    @AfterThrowing(
            pointcut = "execution(* com.ericsson.cac.sprint.adapters.QueryUsageProxyService.*(..))",
            throwing= "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        logger.info("Exception thrown by call to method {} of class {} and exception is ", methodName, className, error.getMessage());
    }
}
