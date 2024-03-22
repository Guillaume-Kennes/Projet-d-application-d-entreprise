package be.vinci.pae.utils.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

/**
 * Custom exception class representing a fatal error in the web application.
 * Extends {@link WebApplicationException}.
 */
public class FatalException extends WebApplicationException {

  /**
   * Constructs a new FatalException with a default error response of HTTP 500 Internal Server Error.
   */
  public FatalException(){
    super(Response.status(Status.INTERNAL_SERVER_ERROR).build());
  }

  /**
   * Constructs a new FatalException with a specified error message and HTTP 500 Internal Server Error response.
   *
   * @param message the error message to be included in the response
   */
  public FatalException(String message){
    super(Response.status(Status.INTERNAL_SERVER_ERROR).entity(message).type("text/plain").build());

  }

}
