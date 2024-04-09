package com.hrms.rest.persistence.dao;

import com.hrms.rest.persistence.entity.Absence;

import jakarta.persistence.EntityManager;

public class AbsenceDao extends GenericDoa<Absence>{
    
    public AbsenceDao(EntityManager entityManager){
        super(entityManager);
        super.setType(Absence.class);
    }
}
