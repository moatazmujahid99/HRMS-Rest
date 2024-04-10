package com.hrms.rest.presentation.controller;

import java.util.List;

import com.hrms.rest.Service.DepartmentService;
import com.hrms.rest.presentation.dto.CreatedDepartmentDTO;
import com.hrms.rest.presentation.dto.DepartmentDTO;
import com.hrms.rest.presentation.dto.InvalidFieldMessageDTO;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/departments")
public class DepartmentController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDepartments() {

        List<DepartmentDTO> departmentDTOs = DepartmentService.getAllDepartments();
        return Response.status(Response.Status.OK)
                .entity(departmentDTOs)
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createDepartment(CreatedDepartmentDTO createdDepartmentDTO) {
        var violations = DepartmentService.validateDepartment(createdDepartmentDTO);
        if (!violations.isEmpty()) {
            List<InvalidFieldMessageDTO> invalidFieldList = DepartmentService.getInvalidFieldList(violations);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(invalidFieldList)
                    .build();
        }
        int departmentId = DepartmentService.createDepartment(createdDepartmentDTO);
        createdDepartmentDTO.setDepartmentId(departmentId);
        return Response.status(Response.Status.CREATED)
                .entity(createdDepartmentDTO)
                .build();
    }

    @POST
    @Path("{departmentId}/manager/{managerId}")
    public Response assignManagerToDepartment(@PathParam("departmentId") int departmentId,
            @PathParam("managerId") int managerId) {

        DepartmentDTO departmentDTO= DepartmentService.assignManagerToDepartment(departmentId, managerId);
        return Response.ok(departmentDTO).build();
    }
}
