package com.hrms.rest.presentation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class EmployeeDTO {
    private Integer employeeId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String jobTitle;
    private String department;

}
