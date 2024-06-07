package com.komelin.komelinopenschoolhw1.repository;

import com.komelin.komelinopenschoolhw1.model.TimeTrackRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeTrackRecordRepository extends JpaRepository<TimeTrackRecord, Long> {
    List<TimeTrackRecord> findAllByMethodName(String methodName);

    List<TimeTrackRecord> findAllByClassName(String className);
}
