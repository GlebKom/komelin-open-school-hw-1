package com.komelin.komelinopenschoolhw1.aspect.timetracking;

import com.komelin.komelinopenschoolhw1.model.TimeTrackRecord;
import com.komelin.komelinopenschoolhw1.service.TrackTimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Component
@Aspect
@Slf4j
@RequiredArgsConstructor
public class TimeTrackAspect {

    private final TrackTimeService trackTimeService;

    @Around(value = "@annotation(com.komelin.komelinopenschoolhw1.annotation.TrackTime)")
    public Object trackAroundSync(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Long startTime = System.currentTimeMillis();
        String methodName = proceedingJoinPoint.getSignature().getName();
        String className = proceedingJoinPoint.getSignature().getDeclaringTypeName();
        try {
            Object result = proceedingJoinPoint.proceed();
            Long endTime = System.currentTimeMillis();
            CompletableFuture.runAsync(
                    () -> saveMethodStats(endTime - startTime, methodName, className)
            );

            return result;
        } catch (Exception e) {
            Long endTime = System.currentTimeMillis();

            CompletableFuture.runAsync(
                    () -> handleMethodThrownException(endTime - startTime,
                            methodName,
                            className,
                            e.getLocalizedMessage())
            );
            throw e;
        }
    }

    @Around(value = "@annotation(com.komelin.komelinopenschoolhw1.annotation.TrackAsyncTime)")
    public Object trackAroundAsync(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Long startTime = System.currentTimeMillis();
        Object proceed = proceedingJoinPoint.proceed();

        String methodName = proceedingJoinPoint.getSignature().getName();
        String className = proceedingJoinPoint.getSignature().getDeclaringTypeName();

        if (proceed instanceof Future future) {
            CompletableFuture.runAsync(
                    () -> {
                        try {
                            future.get();
                            Long endTime = System.currentTimeMillis();
                            saveMethodStats(
                                    endTime - startTime,
                                    methodName,
                                    className);
                        } catch (InterruptedException | ExecutionException e) {
                            Long endTime = System.currentTimeMillis();
                            handleMethodThrownException(endTime - startTime,
                                    methodName,
                                    className,
                                    e.getLocalizedMessage());
                        }
                    }
            );
        } else {
            log.warn("Unable to track method asynchronously because it doesn't return Future type." +
                    " name: {}; className: {}", methodName, className);
        }

        return proceed;
    }


    private void saveMethodStats(Long runtime, String methodName, String className) {
        TimeTrackRecord record = TimeTrackRecord
                .builder()
                .methodName(methodName)
                .className(className)
                .runtime(runtime)
                .startTime(LocalDateTime.now())
                .build();

        trackTimeService.addNewRecord(record);
        throw new RuntimeException();
    }

    private void handleMethodThrownException(Long runtime,
                                            String methodName,
                                            String className,
                                            String exceptionName) {

        TimeTrackRecord record = TimeTrackRecord
                .builder()
                .methodName(methodName)
                .className(className)
                .exception(exceptionName)
                .runtime(runtime)
                .startTime(LocalDateTime.now())
                .build();

        trackTimeService.addNewRecord(record);
    }
}
