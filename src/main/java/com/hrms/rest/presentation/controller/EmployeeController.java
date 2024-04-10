package com.hrms.rest.presentation.controller;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.util.List;
import com.hrms.rest.Service.EmployeeService;
import com.hrms.rest.presentation.dto.AbsenceDTO;
import com.hrms.rest.presentation.dto.AttendanceDTO;
import com.hrms.rest.presentation.dto.CreatedEmployeeDTO;
import com.hrms.rest.presentation.dto.DetailedEmployeeDTO;
import com.hrms.rest.presentation.dto.EmployeeDTO;
import com.hrms.rest.presentation.dto.InvalidFieldMessageDTO;

@Path("/employees")
public class EmployeeController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEmployees(@QueryParam("page") int page,
            @QueryParam("limit") int limit) {

        List<EmployeeDTO> employeeDTOs = EmployeeService.getAllEmployees(page, limit);
        return Response.ok(employeeDTOs).build();
    }

    @GET
    @Path("/{employeeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeById(@PathParam("employeeId") int employeeId, @Context UriInfo uriInfo) {

        DetailedEmployeeDTO detailedEmployeeDTO = EmployeeService.getEmployeeById(employeeId, uriInfo);
        return Response.ok(detailedEmployeeDTO).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createEmployee(CreatedEmployeeDTO createdEmployeeDTO) {

        var violations = EmployeeService.validateEmployee(createdEmployeeDTO);
        if (!violations.isEmpty()) {
            List<InvalidFieldMessageDTO> invalidFieldList = EmployeeService.getInvalidFieldList(violations);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(invalidFieldList)
                    .build();
        }
        int createdEmployeeId = EmployeeService.createEmployee(createdEmployeeDTO);
        createdEmployeeDTO.setEmployeeId(createdEmployeeId);
        return Response.status(Response.Status.CREATED).entity(createdEmployeeDTO).build();
    }

    @GET
    @Path("/{employeeId}/absences")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeAbsences(@PathParam("employeeId") int employeeId,
            @QueryParam("page") int page,
            @QueryParam("limit") int limit) {

        List<AbsenceDTO> absenceDTOs = EmployeeService.getEmployeeAbsences(employeeId, page, limit);
        return Response.ok(absenceDTOs).build();
    }

    @GET
    @Path("/{employeeId}/attendances")
    public Response getEmployeeAttendances(@PathParam("employeeId") int employeeId,
            @QueryParam("page") int page,
            @QueryParam("limit") int limit) {
        
        List<AttendanceDTO> attendanceDTOs = EmployeeService.getEmployeeAttendances(employeeId, page, limit);
        return Response.ok(attendanceDTOs).build();
    }

    @POST
    @Path("/{employeeId}/departments/{departmentId}")
    public Response assignEmployeeToDepartment(@PathParam("employeeId") int employeeId,
    @PathParam("departmentId") int departmentId){
        EmployeeDTO employeeDTO = EmployeeService.assignEmployeeToDepartment(employeeId,departmentId);
        return Response.ok(employeeDTO).build();
    }

    @DELETE
    @Path("/{employeeId}")
    public Response deleteEmployee(@PathParam("employeeId") int employeeId){
        EmployeeService.deleteEmployee(employeeId);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
