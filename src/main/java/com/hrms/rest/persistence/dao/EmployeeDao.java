package com.hrms.rest.persistence.dao;

import java.util.List;

import com.hrms.rest.persistence.entity.Absence;
import com.hrms.rest.persistence.entity.Attendance;
import com.hrms.rest.persistence.entity.Employee;


import jakarta.persistence.EntityManager;

public class EmployeeDao extends GenericDoa<Employee> {

    public EmployeeDao(EntityManager entityManager) {
        super(entityManager);
        super.setType(Employee.class);
    }

    public List<Employee> getAllEmployees(int page, int limit) {
        int offset = (page - 1) * limit;
        return entityManager.createQuery("from Employee", Employee.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<Absence> getEmployeeAbsences(int employeeId, int page, int limit) {
        return entityManager.createQuery("from Absence where employee.id = :employeeId", Absence.class)
                .setParameter("employeeId", employeeId)
                .setFirstResult((page - 1) * limit)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<Attendance> getEmployeeAttendances(int employeeId, int page, int limit) {
        return entityManager.createQuery("from Attendance where employee.id = :employeeId", Attendance.class)
                .setParameter("employeeId", employeeId)
                .setFirstResult((page - 1) * limit)
                .setMaxResults(limit)
                .getResultList();
    }

}
