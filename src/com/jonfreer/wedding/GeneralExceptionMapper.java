package com.jonfreer.wedding;

import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

@Provider
public class GeneralExceptionMapper implements ExceptionMapper<java.lang.Exception>{

	@Override
	public Response toResponse(Exception exception) {
		exception.printStackTrace();
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}

}
