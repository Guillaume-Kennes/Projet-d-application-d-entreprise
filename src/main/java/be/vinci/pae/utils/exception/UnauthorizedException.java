package be.vinci.pae.utils.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

/**
 * Exception class representing an unauthorized access error.
 * This exception extends WebApplicationException
 * to provide custom handling for unauthorized access situations.
 */
public class UnauthorizedException extends WebApplicationException {

  /**
   * Constructs an UnauthorizedException with a default message and status code 401 (Unauthorized).
   */
  public UnauthorizedException() {
    super(Response.status(Status.UNAUTHORIZED).build());
  }

  /**
   * Constructs an UnauthorizedException with a custom message and status code 401 (Unauthorized).
   *
   * @param message The custom message to include in the exception.
   */
  public UnauthorizedException(String message) {
    super(Response.status(Status.UNAUTHORIZED).entity(message).type("text/plain").build());
  }

}
