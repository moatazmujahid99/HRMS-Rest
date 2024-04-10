package com.hrms.rest.presentation.dto;

import java.sql.Timestamp;
import com.hrms.rest.presentation.enums.LeaveStatus;
import com.hrms.rest.presentation.enums.LeaveType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AbsenceDTO {
    private int leaveId;
    private String employeeName;
    private LeaveType leaveType;
    private LeaveStatus leaveStatus;
    private Timestamp leaveStartDate;
    private Timestamp leaveEndDate;
}
