package com.hrms.rest.persistence.dao;

import com.hrms.rest.persistence.entity.Employee;

import jakarta.persistence.EntityManager;

public class EmployeeDao extends GenericDoa<Employee>{

    public EmployeeDao(EntityManager entityManager){
        super(entityManager);
        super.setType(Employee.class);
    }


}
