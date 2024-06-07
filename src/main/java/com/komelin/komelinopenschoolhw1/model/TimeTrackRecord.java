package com.komelin.komelinopenschoolhw1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "time_track_record")
public class TimeTrackRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "method_name")
    private String methodName;

    @Column(name = "class_name")
    private String className;

    @Column(name = "runtime")
    private Long runtime;

    @Column(name = "exception")
    private String exception;

    @Column(name = "start_time")
    private LocalDateTime startTime;

}
