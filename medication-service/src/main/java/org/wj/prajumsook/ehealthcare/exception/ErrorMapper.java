package org.wj.prajumsook.ehealthcare.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ErrorMapper implements ExceptionMapper<Exception> {

  @Override
  public Response toResponse(Exception exe) {
    int statusCode = Response.Status.BAD_REQUEST.getStatusCode();
    if (exe instanceof WebApplicationException) {
      statusCode = ((WebApplicationException) exe).getResponse().getStatus();
    }

    ObjectMapper mapper = new ObjectMapper();
    ObjectNode error = mapper.createObjectNode();
    error.put("exceptionType", exe.getClass().getName());
    error.put("statusCode", statusCode);
    error.put("error", (exe.getMessage() != null) ? exe.getMessage() : "Unknown error");

    return Response.status(statusCode).entity(error).build();

  }
}
