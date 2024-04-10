package com.hrms.rest.Service.mapper;

import java.util.ArrayList;
import java.util.List;

import com.hrms.rest.persistence.entity.Employee;
import com.hrms.rest.presentation.dto.CreatedEmployeeDTO;
import com.hrms.rest.presentation.dto.DetailedEmployeeDTO;
import com.hrms.rest.presentation.dto.EmployeeDTO;

public class EmployeeMapper {

    public static EmployeeDTO EmployeeToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = EmployeeDTO.builder()
                .employeeId(employee.getEmployeeId())
                .fullName(employee.getFirstName() + employee.getLastName())
                .email(employee.getEmail())
                .phoneNumber(employee.getPhoneNumber())
                .jobTitle(employee.getJobTitle())
                .build();

        if (employee.getWorkedDepartment() != null) {
            employeeDTO.setDepartment(employee.getWorkedDepartment().getDepartmentName());
        } else {
            employeeDTO.setDepartment("No department");
        }

        return employeeDTO;
    }

    public static DetailedEmployeeDTO EmployeeToDetailedEmployeeDTO(Employee employee) {
        DetailedEmployeeDTO detailedEmployeeDTO = DetailedEmployeeDTO.builder()
                .employeeId(employee.getEmployeeId())
                .fullName(employee.getFirstName() + employee.getLastName())
                .email(employee.getEmail())
                .phoneNumber(employee.getPhoneNumber())
                .jobTitle(employee.getJobTitle())
                .hireDate(employee.getHireDate())
                .build();

        if (employee.getWorkedDepartment() != null) {
            detailedEmployeeDTO.setDepartment(employee.getWorkedDepartment().getDepartmentName());
        } else {
            detailedEmployeeDTO.setDepartment("No department");
        }

        return detailedEmployeeDTO;
    }

    public static Employee CreatedEmployeeDTOToEmployee(CreatedEmployeeDTO createdEmployeeDTO) {
        Employee employee = new Employee();
        employee.setFirstName(createdEmployeeDTO.getFirstName());
        employee.setLastName(createdEmployeeDTO.getLastName());
        employee.setEmail(createdEmployeeDTO.getEmail());
        employee.setPhoneNumber(createdEmployeeDTO.getPhoneNumber());
        employee.setJobTitle(createdEmployeeDTO.getJobTitle());
        employee.setHireDate(createdEmployeeDTO.getHireDate());
        return employee;
    }

    public static List<EmployeeDTO> EmployeesToEmployeeDTOs(List<Employee> employees) {
        List<EmployeeDTO> employeeDTOs = new ArrayList<>();
        for (Employee employee : employees) {
            EmployeeDTO employeeDTO = EmployeeToEmployeeDTO(employee);
            employeeDTOs.add(employeeDTO);
        }
        return employeeDTOs;
    }
}
