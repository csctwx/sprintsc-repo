/**
 * 
 */
package com.ericsson.cac.sprint.adapters;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

/**
 * @author ebabadd
 *
 */
@Component
@Aspect
public class EnterpriseWalletServiceLogging {
	private static Gson gson = new Gson();
    private Logger logger = LoggerFactory.getLogger(EnterpriseWalletServiceLogging.class);

    @Before("execution(* com.ericsson.cac.sprint.adapters.EnterpriseWalletProxyService.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String arguments = gson.toJson(joinPoint.getArgs());
        logger.info("going to call method {} of class {} with arguments {}", methodName, className, arguments);
    }

    @AfterReturning(
            pointcut = "execution(* com.ericsson.cac.sprint.adapters.EnterpriseWalletProxyService.*(..))",
            returning= "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        logger.info("finished call to method {} of class {} with returned value {}", methodName, className, gson.toJson(result));
    }

    @AfterThrowing(
            pointcut = "execution(* com.ericsson.cac.sprint.adapters.EnterpriseWalletProxyService.*(..))",
            throwing= "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        logger.info("Exception thrown by call to method {} of class {} and exception is ", methodName, className, error.fillInStackTrace());
    }
	
}
