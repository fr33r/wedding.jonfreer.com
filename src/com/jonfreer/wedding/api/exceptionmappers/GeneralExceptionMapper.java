package com.jonfreer.wedding.api.exceptionmappers;

import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

@Provider
public class GeneralExceptionMapper implements ExceptionMapper<java.lang.Exception> {

    @Override
    public Response toResponse(Exception exception) {

        //print the stack trace to the standard output stream.
        exception.printStackTrace();

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("Whoa, that wasn't supposed to happen. Don't worry we are on it.");
        return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
    }

}
