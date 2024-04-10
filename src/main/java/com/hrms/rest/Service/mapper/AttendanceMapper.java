package com.hrms.rest.Service.mapper;

import com.hrms.rest.persistence.entity.Attendance;
import com.hrms.rest.presentation.dto.AttendanceDTO;
import com.hrms.rest.presentation.enums.AttendanceStatus;

public class AttendanceMapper {

    public static AttendanceDTO AttendanceToAttendanceDTO(Attendance attendance) {
       return AttendanceDTO.builder()
                .attendanceId(attendance.getAttendanceId())
                .attendanceDate(attendance.getAttendanceDate())
                .status(AttendanceStatus.valueOf(attendance.getStatus()))
                .build();
    }
    
}
