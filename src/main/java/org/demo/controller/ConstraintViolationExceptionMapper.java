package org.demo.controller;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

	@Override
	public Response toResponse(ConstraintViolationException exception) {
		String json = String.format("{\"validation-error\":\"%s\"}", exception.getMessage());
		return Response.status(Response.Status.BAD_REQUEST).entity(json).build();
	}

}
