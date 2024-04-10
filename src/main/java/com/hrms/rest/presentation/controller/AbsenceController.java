package com.hrms.rest.presentation.controller;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import com.hrms.rest.Service.AbsenceService;
import com.hrms.rest.presentation.dto.CreatedAbsenceDTO;
import com.hrms.rest.presentation.dto.InvalidFieldMessageDTO;
import com.hrms.rest.presentation.dto.UpadatedAbsenceDTO;

@Path("/absences")
public class AbsenceController {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAbsence(CreatedAbsenceDTO createdAbsenceDTO) {

        var violations = AbsenceService.validateAbsence(createdAbsenceDTO);
        if (!violations.isEmpty()) {
            List<InvalidFieldMessageDTO> invalidFieldList = AbsenceService.getInvalidFieldList(violations);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(invalidFieldList)
                    .build();
        }
        int createdAbsenceId = AbsenceService.createAbsence(createdAbsenceDTO);
        createdAbsenceDTO.setLeaveId(createdAbsenceId);
        return Response.status(Response.Status.CREATED)
                .entity(createdAbsenceDTO)
                .build();
    }

    @PATCH
    @Path("/{leaveId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAbsence(UpadatedAbsenceDTO upadatedAbsenceDTO,
            @PathParam("leaveId") int leaveId) {

        var violations = AbsenceService.validateLeaveStatus(upadatedAbsenceDTO);
        if (!violations.isEmpty()) {
            InvalidFieldMessageDTO InvalidLeaveStatusMessage = AbsenceService.getInvalidLeaveStatusMessage(violations);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(InvalidLeaveStatusMessage)
                    .build();
        }

        AbsenceService.updateAbsence(upadatedAbsenceDTO,leaveId);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
