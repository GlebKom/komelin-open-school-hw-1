package com.komelin.komelinopenschoolhw1.controller;


import com.komelin.komelinopenschoolhw1.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hidden")
@RequiredArgsConstructor
public class TestMethodInvocationController {

    private final TestService testService;
    @GetMapping("/testMethodInvocation")
    public ResponseEntity<?> invokeTestMethod() {
        testService.syncTestMethod();
        testService.asyncTestMethod();
        testService.asyncThrowsException();
        return ResponseEntity.ok("Methods invoked: 3");
    }
}
