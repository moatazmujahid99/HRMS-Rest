package com.hrms.rest.presentation.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class InvalidFieldMessageDTO {
    private String field;
    private String message;
}
