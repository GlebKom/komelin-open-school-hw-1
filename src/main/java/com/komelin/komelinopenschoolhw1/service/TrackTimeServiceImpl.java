package com.komelin.komelinopenschoolhw1.service;

import com.komelin.komelinopenschoolhw1.model.TimeTrackRecord;
import com.komelin.komelinopenschoolhw1.model.dto.MethodStatsDto;
import com.komelin.komelinopenschoolhw1.repository.TimeTrackRecordRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrackTimeServiceImpl implements TrackTimeService{

    private final TimeTrackRecordRepository timeTrackRecordRepository;

    @Override
    public void addNewRecord(TimeTrackRecord record) {
        timeTrackRecordRepository.save(record);
    }

    @Override
    public TimeTrackRecord getRecordById(Long id) {
        return timeTrackRecordRepository.getReferenceById(id);
    }

    @Override
    public List<TimeTrackRecord> getRecordsByMethodName(String methodName) {
        return timeTrackRecordRepository.findAllByMethodName(methodName);
    }

    @Override
    public List<TimeTrackRecord> getRecordsByClassName(String className) {
        return timeTrackRecordRepository.findAllByClassName(className);
    }

    @Override
    public MethodStatsDto getStatsByMethodName(String methodName) {
        List<TimeTrackRecord> records = getRecordsByMethodName(methodName);
        return getStats(records, methodName);
    }

    @Override
    public List<MethodStatsDto> getStatsByClassName(String className) {
        List<MethodStatsDto> stats = new ArrayList<>();
        List<TimeTrackRecord> records = getRecordsByClassName(className);
        return records.stream()
                .collect(Collectors.groupingBy(TimeTrackRecord::getMethodName))
                .entrySet()
                .stream()
                .map(record1 -> getStats(record1.getValue(), record1.getKey()))
                .collect(Collectors.toList());
    }

    private MethodStatsDto getStats(List<TimeTrackRecord> records, String methodName) {
        double avgRuntime = records.stream()
                .mapToLong(TimeTrackRecord::getRuntime)
                .average()
                .orElse(0);
        long exceptionOccurred = records.stream()
                .filter(record -> record.getException() != null)
                .count();
        long longestRuntime = records.stream()
                .mapToLong(TimeTrackRecord::getRuntime)
                .max()
                .orElse(0);

        return MethodStatsDto.builder()
                .methodName(methodName)
                .avgRuntime(avgRuntime)
                .exceptionsOccurred(exceptionOccurred)
                .longestRuntime(longestRuntime)
                .build();
    }
}
