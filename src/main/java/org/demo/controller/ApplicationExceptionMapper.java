package org.demo.controller;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.demo.ApplicationException;

@Provider
public class ApplicationExceptionMapper implements ExceptionMapper<ApplicationException> {

	@Override
	public Response toResponse(ApplicationException exception) {
		String json = String.format("{\"error\":\"%s\"}", exception.getMessage());
		return Response.status(Response.Status.BAD_REQUEST).entity(json).build();
	}

}
