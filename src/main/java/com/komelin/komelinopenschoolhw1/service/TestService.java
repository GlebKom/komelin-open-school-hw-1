package com.komelin.komelinopenschoolhw1.service;

import com.komelin.komelinopenschoolhw1.annotation.TrackAsyncTime;
import com.komelin.komelinopenschoolhw1.annotation.TrackTime;
import com.komelin.komelinopenschoolhw1.exception.TestException;
import com.komelin.komelinopenschoolhw1.util.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class TestService {

    @TrackTime
    public void syncTestMethod() {
        Random random = new Random();
        int timeToSleep = random.nextInt(100, 1000);
        ThreadUtil.sleep(timeToSleep);
    }
    @TrackAsyncTime
    public CompletableFuture<Void> asyncTestMethod() {
        return CompletableFuture.runAsync(
                () -> ThreadUtil.sleep(1000)
        );
    }
    @TrackAsyncTime
    public CompletableFuture<Void> asyncThrowsException() {
        return CompletableFuture.runAsync(
                () -> {
                    throw new TestException();
                }
        );
    }
}
