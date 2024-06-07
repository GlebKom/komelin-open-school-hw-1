package com.komelin.komelinopenschoolhw1.service;

import com.komelin.komelinopenschoolhw1.model.TimeTrackRecord;
import com.komelin.komelinopenschoolhw1.model.dto.MethodStatsDto;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.List;

public interface TrackTimeService {

    void addNewRecord(TimeTrackRecord record);

    TimeTrackRecord getRecordById(Long id);

    List<TimeTrackRecord> getRecordsByMethodName(String methodName);

    List<TimeTrackRecord> getRecordsByClassName(String className);

    MethodStatsDto getStatsByMethodName(String methodName);

    List<MethodStatsDto> getStatsByClassName(String className);
}
