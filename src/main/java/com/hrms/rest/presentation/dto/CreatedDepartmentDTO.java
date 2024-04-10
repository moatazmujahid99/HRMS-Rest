package com.hrms.rest.presentation.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CreatedDepartmentDTO {
    private int departmentId;
    @Size(min = 3,max = 100,message = "The department name must be between 3 and 100")
    private String departmentName;
}
