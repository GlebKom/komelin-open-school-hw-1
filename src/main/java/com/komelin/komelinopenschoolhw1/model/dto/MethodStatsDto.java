package com.komelin.komelinopenschoolhw1.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MethodStatsDto {

    private String methodName;
    private double avgRuntime;
    private long exceptionsOccurred;
    private long longestRuntime;
}
