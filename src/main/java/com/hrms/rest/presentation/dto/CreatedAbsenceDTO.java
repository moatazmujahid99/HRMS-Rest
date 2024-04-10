package com.hrms.rest.presentation.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CreatedAbsenceDTO {

    private Integer leaveId;
    
    @NotNull(message = "Employee id is required")
    @Pattern(regexp = "\\d+", message = "Employee id must be a number")
    private String employeeId;

    @NotNull(message = "Leave start date is required")
    @Pattern(
        regexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}",
        message = "Leave start date must be in the format 'yyyy-MM-dd HH:mm:ss'"
    )
    private String leaveStartDate;

    @NotNull(message = "Leave end date is required")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}", message = "Leave end date must be in the format 'yyyy-MM-dd HH:mm:ss'")
    private String leaveEndDate;
    
    @NotNull(message = "Leave type is required")
    @Pattern(regexp = "Sick|Vacation|Maternity|Paternity|Unpaid|Other", message = "leave type must be Sick|Vacation|Maternity|Paternity|Unpaid|Other")
    private String leaveType;
}
