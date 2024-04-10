package com.hrms.rest.presentation.dto;

import java.sql.Date;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CreatedEmployeeDTO{
    private int employeeId;
    @Size(min = 3,max = 50,message = "Your first name must be between 3 and 50")
    private String firstName;
    
    @Size(min = 3,max = 50,message = "Your last name must be between 3 and 50")
    private String lastName;

    @Email(message = "Invalid email address")
    private String email;

    @Pattern(regexp = "\\d{11}", message = "Phone number must be a 11-digit number")
    private String phoneNumber;

    @NotNull(message = "Job title is required")
    private String jobTitle;

    @NotNull(message = "Hire date is required")
    @JsonbDateFormat(value = "yyyy-MM-dd")
    private Date hireDate;
}
