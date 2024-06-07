package com.komelin.komelinopenschoolhw1.controller;


import com.komelin.komelinopenschoolhw1.model.dto.MethodStatsDto;
import com.komelin.komelinopenschoolhw1.service.TrackTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TimeStatsController {

    private final TrackTimeService trackTimeService;

    @GetMapping("/method/{methodName}")
    public ResponseEntity<MethodStatsDto> getStatsByMethodName(@PathVariable(name = "methodName") String methodName) {
        return ResponseEntity.ok(trackTimeService.getStatsByMethodName(methodName));
    }

    @GetMapping("/class/{className}")
    public ResponseEntity<List<MethodStatsDto>> getStatsByClassName(@PathVariable(name = "className") String className) {
        return ResponseEntity.ok(trackTimeService.getStatsByClassName(className));
    }
}
