// package com.hrms.rest.presentation.exceptionMapper;

// import com.hrms.rest.presentation.dto.ErrorMessageDTO;

// import jakarta.ws.rs.core.Response;
// import jakarta.ws.rs.ext.ExceptionMapper;
// import jakarta.ws.rs.ext.Provider;

// @Provider
// public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

//     @Override
//     public Response toResponse(Throwable exception) {
//         ErrorMessageDTO error = ErrorMessageDTO.builder()
//                 .message(exception.getMessage())
//                 .errorCode(500)
//                 .build();

//         return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
//     }

// }
