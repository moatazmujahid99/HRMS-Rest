package com.hrms.rest.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import com.hrms.rest.Service.mapper.DepartmentMapper;
import com.hrms.rest.persistence.Database;
import com.hrms.rest.persistence.dao.DepartmentDao;
import com.hrms.rest.persistence.dao.EmployeeDao;
import com.hrms.rest.persistence.entity.Department;
import com.hrms.rest.persistence.entity.Employee;
import com.hrms.rest.presentation.dto.CreatedDepartmentDTO;
import com.hrms.rest.presentation.dto.DepartmentDTO;
import com.hrms.rest.presentation.dto.InvalidFieldMessageDTO;
import com.hrms.rest.presentation.exception.DataNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

public class DepartmentService {

    public static Set<ConstraintViolation<CreatedDepartmentDTO>> validateDepartment(
            CreatedDepartmentDTO createdDepartmentDTO) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        return validator.validate(createdDepartmentDTO);
    }

    public static List<InvalidFieldMessageDTO> getInvalidFieldList(
            Set<ConstraintViolation<CreatedDepartmentDTO>> violations) {

        List<InvalidFieldMessageDTO> invalidFieldList = new ArrayList<>();

        for (ConstraintViolation<CreatedDepartmentDTO> violation : violations) {
            InvalidFieldMessageDTO invalidFieldMessageDTO = InvalidFieldMessageDTO.builder()
                    .field(violation.getPropertyPath().toString())
                    .message(violation.getMessage())
                    .build();
            invalidFieldList.add(invalidFieldMessageDTO);
        }
        return invalidFieldList;
    }

    public static int createDepartment(CreatedDepartmentDTO createdDepartmentDTO) {
        return Database.doInTransaction(em -> {
            DepartmentDao departmentDao =new DepartmentDao(em);
            Department department = DepartmentMapper.CreatedDepartmentDTOToDepartment(createdDepartmentDTO);
            departmentDao.create(department);
            return department.getDepartmentId();
        });
    }

    public static List<DepartmentDTO> getAllDepartments() {
        return Database.doInTransaction(em -> {
            DepartmentDao departmentDao = new DepartmentDao(em);
            List<Department> departments = departmentDao.getAll();
            return DepartmentMapper.DepartmentToDepartmentDTO(departments);
        });
    }

    public static DepartmentDTO assignManagerToDepartment(int departmentId, int managerId) {
        return Database.doInTransaction(em->{
            Employee manager = getManagerOrThrow(managerId,em);
            Department department = getDepartmentOrThrow(departmentId, em);
            assignManagerToDepartment(manager, department);
            return DepartmentMapper.DepartmentToDepartmentDTO(department);
        });
    }

    private static void assignManagerToDepartment(Employee manager, Department department) {
        manager.setManagedDepartment(department);
        department.setManager(manager);
    }

    private static Department getDepartmentOrThrow(int departmentId, EntityManager em) {
        DepartmentDao departmentDao = new DepartmentDao(em);
        Optional<Department> department = departmentDao.get(departmentId);
        if (!department.isPresent())
            throw new DataNotFoundException("Department with ID " + departmentId + " not found");
    
        return department.get();
    }

    private static Employee getManagerOrThrow(int managerId, EntityManager em) {
        EmployeeDao employeeDao = new EmployeeDao(em);
        Optional<Employee> manager = employeeDao.get(managerId);
        if(!manager.isPresent()){
            throw new DataNotFoundException("Manager with ID " + managerId + " not found");
        }
        return manager.get();
    }

}
