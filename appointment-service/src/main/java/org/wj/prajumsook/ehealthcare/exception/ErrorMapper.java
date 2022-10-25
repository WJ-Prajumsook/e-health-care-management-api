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
  public Response toResponse(Exception ex) {
    int statusCode = Response.Status.BAD_REQUEST.getStatusCode();
    if(ex instanceof WebApplicationException) {
      statusCode = ((WebApplicationException)ex).getResponse().getStatus();
    }

    ObjectNode error = new ObjectMapper().createObjectNode()
        .put("exceptionType", ex.getClass().getName())
        .put("statusCode", statusCode)
        .put("error", (ex.getMessage() != null) ? ex.getMessage() : "Unknown error");

    return Response.status(statusCode).entity(error).build();
  }

}
