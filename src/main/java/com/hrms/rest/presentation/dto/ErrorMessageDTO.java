package com.hrms.rest.presentation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ErrorMessageDTO {
    private String message;
    private int errorCode;
}
