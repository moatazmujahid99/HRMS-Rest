package com.hrms.rest.presentation.dto;

import java.sql.Timestamp;

import com.hrms.rest.presentation.enums.AttendanceStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AttendanceDTO {
    private Integer attendanceId;
    private Timestamp attendanceDate;
    private AttendanceStatus status;
}
