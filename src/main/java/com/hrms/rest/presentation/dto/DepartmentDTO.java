package com.hrms.rest.presentation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class DepartmentDTO {
    private int departmentId;
    private String departmentName;
    private String managerName;
}
