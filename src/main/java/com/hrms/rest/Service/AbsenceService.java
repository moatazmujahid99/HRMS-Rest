package com.hrms.rest.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.hrms.rest.Service.mapper.AbsenceMapper;
import com.hrms.rest.persistence.Database;
import com.hrms.rest.persistence.dao.AbsenceDao;
import com.hrms.rest.persistence.dao.EmployeeDao;
import com.hrms.rest.persistence.entity.Absence;
import com.hrms.rest.persistence.entity.Employee;
import com.hrms.rest.presentation.dto.CreatedAbsenceDTO;
import com.hrms.rest.presentation.dto.InvalidFieldMessageDTO;
import com.hrms.rest.presentation.dto.UpadatedAbsenceDTO;
import com.hrms.rest.presentation.exception.DataNotFoundException;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

public class AbsenceService {

    public static Set<ConstraintViolation<CreatedAbsenceDTO>> validateAbsence(CreatedAbsenceDTO createdAbsenceDTO) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        return validator.validate(createdAbsenceDTO);
    }

    public static List<InvalidFieldMessageDTO> getInvalidFieldList(
            Set<ConstraintViolation<CreatedAbsenceDTO>> violations) {

        List<InvalidFieldMessageDTO> invalidFieldList = new ArrayList<>();

        for (ConstraintViolation<CreatedAbsenceDTO> violation : violations) {
            InvalidFieldMessageDTO invalidFieldMessageDTO = InvalidFieldMessageDTO.builder()
                    .field(violation.getPropertyPath().toString())
                    .message(violation.getMessage())
                    .build();
            invalidFieldList.add(invalidFieldMessageDTO);
        }
        return invalidFieldList;
    }

    public static int createAbsence(CreatedAbsenceDTO createdAbsenceDTO) {
        return Database.doInTransaction(em -> {

            Employee employee = getEmployeeOrThrow(Integer.parseInt(createdAbsenceDTO.getEmployeeId()), em);

            AbsenceDao absenceDao = new AbsenceDao(em);

            Absence absence = AbsenceMapper.CreatedAbsenceDTOToAbsence(createdAbsenceDTO);
            absence.setEmployee(employee);

            absenceDao.create(absence);
            return absence.getLeaveId();
        });
    }

    public static Set<ConstraintViolation<UpadatedAbsenceDTO>> validateLeaveStatus(
            UpadatedAbsenceDTO upadatedAbsenceDTO) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        return validator.validate(upadatedAbsenceDTO);
    }

    public static InvalidFieldMessageDTO getInvalidLeaveStatusMessage(
            Set<ConstraintViolation<UpadatedAbsenceDTO>> violations) {

        ConstraintViolation<UpadatedAbsenceDTO> violation = violations.iterator().next();
        return InvalidFieldMessageDTO.builder()
                .field(violation.getPropertyPath().toString())
                .message(violation.getMessage())
                .build();
    }

    public static void updateAbsence(UpadatedAbsenceDTO upadatedAbsenceDTO, int leaveId) {
        Database.doInTransactionWithoutResult(em -> {
            
            Absence absence = getAbsenceOrThrow(leaveId, em);
            absence.setLeaveStatus(upadatedAbsenceDTO.getLeaveStatus());
        });
    }

   

    private static Absence getAbsenceOrThrow(int leaveId, EntityManager em) {
        AbsenceDao absenceDao = new AbsenceDao(em);
        Optional<Absence> absence = absenceDao.get(leaveId);
        if (!absence.isPresent()) {
            throw new DataNotFoundException("Absence with ID " + leaveId + " not found");
        }
        return absence.get();
    }

    private static Employee getEmployeeOrThrow(int employeeId, EntityManager em) {
        EmployeeDao employeeDao = new EmployeeDao(em);
        Optional<Employee> employee = employeeDao.get(employeeId);
        if (!employee.isPresent())
            throw new DataNotFoundException("Employee with ID " + employeeId + " not found");

        return employee.get();
    }

}
