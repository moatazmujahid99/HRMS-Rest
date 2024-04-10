package com.hrms.rest.presentation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.sql.Date;
import java.util.List;


@Builder
@Getter
@Setter
public class DetailedEmployeeDTO {
    
    private Integer employeeId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String jobTitle;
    private String department;
    private Date hireDate;
    private List<LinkDTO> links;

    public void addLink(String url,String rel){
        links.add(new LinkDTO(url, rel));
    }
    
}
