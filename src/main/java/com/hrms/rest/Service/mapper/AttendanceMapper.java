package com.hrms.rest.Service.mapper;

import java.util.ArrayList;
import java.util.List;

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

    public static List<AttendanceDTO> AttendancesToAttendanceDTOs(List<Attendance> attendances) {
        List<AttendanceDTO> attendanceDTOs = new ArrayList<>();
        for (Attendance attendance : attendances) {
            AttendanceDTO attendanceDTO = AttendanceToAttendanceDTO(attendance);
            attendanceDTOs.add(attendanceDTO);
        }
        return attendanceDTOs;
    }
    
}
