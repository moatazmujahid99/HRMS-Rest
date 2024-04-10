package com.hrms.rest.Service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.hrms.rest.Service.mapper.AbsenceMapper;
import com.hrms.rest.Service.mapper.AttendanceMapper;
import com.hrms.rest.Service.mapper.EmployeeMapper;
import com.hrms.rest.persistence.Database;
import com.hrms.rest.persistence.dao.DepartmentDao;
import com.hrms.rest.persistence.dao.EmployeeDao;
import com.hrms.rest.persistence.entity.Absence;
import com.hrms.rest.persistence.entity.Attendance;
import com.hrms.rest.persistence.entity.Department;
import com.hrms.rest.persistence.entity.Employee;
import com.hrms.rest.presentation.controller.EmployeeController;
import com.hrms.rest.presentation.dto.AbsenceDTO;
import com.hrms.rest.presentation.dto.AttendanceDTO;
import com.hrms.rest.presentation.dto.CreatedEmployeeDTO;
import com.hrms.rest.presentation.dto.DetailedEmployeeDTO;
import com.hrms.rest.presentation.dto.EmployeeDTO;
import com.hrms.rest.presentation.dto.InvalidFieldMessageDTO;
import com.hrms.rest.presentation.exception.DataNotFoundException;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.ws.rs.core.UriInfo;

public class EmployeeService {

    public EmployeeService() {

    }

    public static List<EmployeeDTO> getAllEmployees(int page, int limit) {
        return Database.doInTransaction(em -> {
            
            EmployeeDao employeeDao = new EmployeeDao(em);
            List<Employee> employees = employeeDao.getAllEmployees(page, limit);
            return EmployeeMapper.EmployeesToEmployeeDTOs(employees);
        });

    }

    public static DetailedEmployeeDTO getEmployeeById(int employeeId, UriInfo uriInfo) {
        return Database.doInTransaction(em -> {
            
            Employee employee = getEmployeeOrThrow(employeeId, em);
            return detailedEmployeeDTOfrom(employee, uriInfo);

        });
    }

    public static Set<ConstraintViolation<CreatedEmployeeDTO>> validateEmployee(CreatedEmployeeDTO createdEmployeeDTO) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        return validator.validate(createdEmployeeDTO);
    }

    public static int createEmployee(CreatedEmployeeDTO createdEmployeeDTO) {
        return Database.doInTransaction(em -> {
            EmployeeDao employeeDao = new EmployeeDao(em);
            Employee employee = EmployeeMapper.CreatedEmployeeDTOToEmployee(createdEmployeeDTO);
            employeeDao.create(employee);
            return employee.getEmployeeId();
        });
    }

    public static List<InvalidFieldMessageDTO> getInvalidFieldList(
            Set<ConstraintViolation<CreatedEmployeeDTO>> violations) {

        List<InvalidFieldMessageDTO> invalidFieldList = new ArrayList<>();
        
        for (ConstraintViolation<CreatedEmployeeDTO> violation : violations) {
            InvalidFieldMessageDTO invalidFieldMessageDTO = InvalidFieldMessageDTO.builder()
                    .field(violation.getPropertyPath().toString())
                    .message(violation.getMessage())
                    .build();
            invalidFieldList.add(invalidFieldMessageDTO);
        }
        return invalidFieldList;

    }

    public static List<AbsenceDTO> getEmployeeAbsences(int employeeId, int page, int limit) {
        return Database.doInTransaction(em -> {

            Employee employee = getEmployeeOrThrow(employeeId, em);
            List<Absence> absences = new EmployeeDao(em).getEmployeeAbsences(employee.getEmployeeId(), page, limit);
            return AbsenceMapper.AbsencesToAbsenceDTOs(absences);
        });
    }

    public static List<AttendanceDTO> getEmployeeAttendances(int employeeId, int page, int limit) {
        return Database.doInTransaction(em -> {

            Employee employee = getEmployeeOrThrow(employeeId, em);
            List<Attendance> attendances = new EmployeeDao(em).getEmployeeAttendances(employee.getEmployeeId(), page, limit);
            return AttendanceMapper.AttendancesToAttendanceDTOs(attendances);
        });

    }

    public static EmployeeDTO assignEmployeeToDepartment(int employeeId, int departmentId) {
        return Database.doInTransaction(em -> {
            Employee employee = getEmployeeOrThrow(employeeId, em);
            Department department = getDepartmentOrThrow(departmentId, em);
            assignEmployeeToDepartment(employee, department);
            return EmployeeMapper.EmployeeToEmployeeDTO(employee);
        });
    }
    
    
    public static void deleteEmployee(int employeeId) {
        Database.doInTransactionWithoutResult(em -> {
            Employee employee = getEmployeeOrThrow(employeeId, em);
            
            Department managedDepartment = employee.getManagedDepartment();
            if (managedDepartment != null) {
                managedDepartment.setManager(null);
            }
            
            new EmployeeDao(em).delete(employee);
        });
    }

    private static void assignEmployeeToDepartment(Employee employee, Department department) {
        employee.setWorkedDepartment(department);
        department.getEmployees().add(employee);
    }
    
    private static Department getDepartmentOrThrow(int departmentId, EntityManager em) {
        DepartmentDao departmentDao = new DepartmentDao(em);
        Optional<Department> department = departmentDao.get(departmentId);
        if (!department.isPresent())
            throw new DataNotFoundException("Department with ID " + departmentId + " not found");
    
        return department.get();
    }
    
    private static Employee getEmployeeOrThrow(int employeeId, EntityManager em) {
        EmployeeDao employeeDao = new EmployeeDao(em);
        Optional<Employee> employee = employeeDao.get(employeeId);
        if (!employee.isPresent())
            throw new DataNotFoundException("Employee with ID " + employeeId + " not found");
    
        return employee.get();
    }
    

    private static DetailedEmployeeDTO detailedEmployeeDTOfrom(Employee employee, UriInfo uriInfo) {
        DetailedEmployeeDTO detailedEmployeeDTO = EmployeeMapper.EmployeeToDetailedEmployeeDTO(employee);
        detailedEmployeeDTO.setLinks(new ArrayList<>());
        detailedEmployeeDTO.addLink(getUriForAbsences(uriInfo, detailedEmployeeDTO), "absences");
        detailedEmployeeDTO.addLink(getUriForAttendances(uriInfo, detailedEmployeeDTO), "attendances");
        return detailedEmployeeDTO;
    }

    private static String getUriForAttendances(UriInfo uriInfo, DetailedEmployeeDTO detailedEmployeeDTO) {
        URI uri = uriInfo.getBaseUriBuilder()
                .path(EmployeeController.class)
                .path(Integer.toString(detailedEmployeeDTO.getEmployeeId()))
                .path("attendances")
                .queryParam("page", "pageNo")
                .queryParam("limit", "limit")
                .build();
        return uri.toString();
    }

    private static String getUriForAbsences(UriInfo uriInfo, DetailedEmployeeDTO detailedEmployeeDTO) {
        URI uri = uriInfo.getBaseUriBuilder()
                .path(EmployeeController.class)
                .path(Integer.toString(detailedEmployeeDTO.getEmployeeId()))
                .path("absences")
                .queryParam("page", "pageNo")
                .queryParam("limit", "limit")
                .build();
        return uri.toString();
    }


}
