package com.hrms.rest.presentation.exceptionMapper;

import com.hrms.rest.presentation.dto.ErrorMessageDTO;
import com.hrms.rest.presentation.exception.DataNotFoundException;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException>{

    @Override
    public Response toResponse(DataNotFoundException exception) {
        ErrorMessageDTO errorMessage= ErrorMessageDTO.builder()
                .message(exception.getMessage())
                .errorCode(404)
                .build();
        return Response.status(Response.Status.NOT_FOUND).entity(errorMessage).build();
    }
    
}
