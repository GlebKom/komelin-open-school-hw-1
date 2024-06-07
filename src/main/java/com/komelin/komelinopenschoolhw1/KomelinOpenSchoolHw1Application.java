package com.komelin.komelinopenschoolhw1;

import com.komelin.komelinopenschoolhw1.service.TestService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@RequiredArgsConstructor
@EnableAsync
public class KomelinOpenSchoolHw1Application {

    private final TestService testService;
    public static void main(String[] args) {
        SpringApplication.run(KomelinOpenSchoolHw1Application.class, args);
    }

}
