package com.hrms.rest.persistence.dao;

import com.hrms.rest.persistence.entity.Department;

import jakarta.persistence.EntityManager;

public class DepartmentDao extends GenericDoa<Department>{

    public DepartmentDao(EntityManager entityManager){
        super(entityManager);
        super.setType(Department.class);
    }
    
}
