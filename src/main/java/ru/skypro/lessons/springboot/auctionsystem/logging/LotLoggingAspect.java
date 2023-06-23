package ru.skypro.lessons.springboot.auctionsystem.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
@Component
@Aspect
public class LotLoggingAspect {
    @Pointcut("within(ru.skypro.lessons.springboot.auctionsystem.service.*)")
    public void lotLogging(){
    }

    @After("lotLogging()")
    public void logAfterMethod(JoinPoint jp){
        Logger logger = LoggerFactory.getLogger(jp.getTarget().getClass());
        String methodName = jp.getSignature().getName();

        Object[] args = jp.getArgs();
        CodeSignature codeSignature = (CodeSignature) jp.getSignature();
        StringBuilder argsString = new StringBuilder();
        for (int argIndex = 0; argIndex < args.length;argIndex++){
            argsString.append(codeSignature.getParameterNames()[argIndex]).append("=").append(args[argIndex].toString());
            if(argIndex < args.length - 1){
                argsString.append(", ");
            }
        }

        logger.debug("Был вызван метод: "+methodName+(argsString.isEmpty() ? " без параметров" :" с параметрами: "+ argsString));
    }
    @AfterThrowing(value = "lotLogging()",throwing = "exception")
    public void logAfterThrowing(JoinPoint jp, Exception exception){
        Logger logger = LoggerFactory.getLogger(jp.getTarget().getClass());
        logger.error(exception.getMessage(),exception);
    }
}
