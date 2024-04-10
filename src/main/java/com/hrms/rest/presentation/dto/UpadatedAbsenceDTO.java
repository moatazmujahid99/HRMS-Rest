package com.hrms.rest.presentation.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UpadatedAbsenceDTO {
    @NotNull(message = "Leave status is required")
    @Pattern(regexp = "Approved|Rejected|Pending", message = "leave status must be Approved|Rejected|Pending")
    private String leaveStatus;
}
