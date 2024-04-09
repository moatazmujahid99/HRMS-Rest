package com.hrms.rest;

import com.hrms.rest.persistence.dept;
import com.hrms.rest.persistence.entity.Employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIt() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hrms");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Employee emp = entityManager.find(Employee.class, 1);
        dept d=new dept();
        d.id=emp.getManagedDepartment().getDepartmentId();
        d.name=emp.getManagedDepartment().getDepartmentName();
        System.out.println(emp.getManagedDepartment());
    
        return Response.ok(d).build();
    }
    
}
