package com.jonfreer.wedding.api.exceptionmappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import com.jonfreer.wedding.application.exceptions.ResourceNotFoundException;

public class NotFoundExceptionMapper implements ExceptionMapper<ResourceNotFoundException> {

	@Override
	public Response toResponse(ResourceNotFoundException resourceNotFoundException) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage(resourceNotFoundException.getLocalizedMessage());
		return Response.status(Status.NOT_FOUND).entity(errorResponse).build();
	}

}
