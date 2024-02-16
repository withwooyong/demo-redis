package com.example.demoredis.config;


import com.example.demoredis.util.SlackUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Optional;

@Aspect
@Log4j2
@Component
@RequiredArgsConstructor
public class LogAspect {

    private final SlackUtil slackUtil;

    // 패키지 범위 설정
    @Pointcut("execution(* com.example.*..*Controller.*(..))")
    public void controllerMethods() {
    }

    // 메소드 실행 전
    @Before("controllerMethods()")
    public void logBefore(JoinPoint joinPoint) {
        log.info("request {}", joinPoint.getSignature().toShortString());
        Arrays.stream(joinPoint.getArgs()).map(Object::toString).map(str -> "\t" + str).forEach(log::info);
    }

    // 메소드 리턴 값 반환 후
    @AfterReturning(pointcut = "controllerMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("response {}", joinPoint.getSignature().toShortString());
        if (result == null) return;
        log.info("\t{}", result.toString());
    }

    // Exception 발생 시 모니터링 하기위해 Slack 으로 전송 (추후 Jandi 로 변경)
    @AfterThrowing(pointcut = "controllerMethods()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) throws UnknownHostException {
        String methodName = joinPoint.getSignature().toShortString();
        String errorMsg = exception.getMessage();
        log.error("error {}", methodName);
        log.error("\t errorMsg: {}", errorMsg);
        String agentId = Optional.ofNullable(System.getProperty("pinpoint.agentId")).orElse("agentId");
        String applicationName = Optional.ofNullable(System.getProperty("pinpoint.applicationName")).orElse("applicationName");
        String ip = InetAddress.getLocalHost().getHostAddress();
        String error = String.format("agentId:%s\nip:%s\napplicationName:%s\n%s\n%s", agentId, ip, applicationName, methodName, errorMsg);
        slackUtil.send(error);
    }
}
