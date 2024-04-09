package com.hrms.rest.persistence.dao;

import com.hrms.rest.persistence.entity.Attendance;

import jakarta.persistence.EntityManager;

public class AttendanceDao extends GenericDoa<Attendance>{
    
    public AttendanceDao(EntityManager entityManager){
        super(entityManager);
        super.setType(Attendance.class);
    }
}
