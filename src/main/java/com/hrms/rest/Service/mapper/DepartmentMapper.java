package com.hrms.rest.Service.mapper;

import java.util.ArrayList;
import java.util.List;

import com.hrms.rest.persistence.entity.Department;
import com.hrms.rest.presentation.dto.CreatedDepartmentDTO;
import com.hrms.rest.presentation.dto.DepartmentDTO;

public class DepartmentMapper {

    public static DepartmentDTO DepartmentToDepartmentDTO(Department department) {
        DepartmentDTO departmentDTO = DepartmentDTO.builder()
                .departmentId(department.getDepartmentId())
                .departmentName(department.getDepartmentName())
                .build();
        if (department.getManager() != null) {
            departmentDTO.setManagerName(
                    department.getManager().getFirstName() + " " + department.getManager().getLastName());
        } else {
            departmentDTO.setManagerName("No Manager");
        }
        return departmentDTO;
    }

    public static Department CreatedDepartmentDTOToDepartment(CreatedDepartmentDTO createdDepartmentDTO) {
        Department department = new Department();
        department.setDepartmentName(createdDepartmentDTO.getDepartmentName());
        return department;
    }

    public static List<DepartmentDTO> DepartmentToDepartmentDTO(List<Department> departments) {
        List<DepartmentDTO> departmentDTOs = new ArrayList<>();
        for (Department department : departments) {
            DepartmentDTO departmentDTO = DepartmentToDepartmentDTO(department);
            departmentDTOs.add(departmentDTO);
        }
        return departmentDTOs;
    }

}
