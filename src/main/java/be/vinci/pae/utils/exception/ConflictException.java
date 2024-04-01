package be.vinci.pae.utils.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

/**
 * Represents an exception indicating a conflict has occurred.
 * Extends WebApplicationException for HTTP status code 409 (Conflict).
 */
public class ConflictException extends WebApplicationException {

  /**
   * Constructs a new ConflictException with no detail message.
   * This constructor creates an instance with HTTP status code 409 (Conflict).
   */
  public ConflictException() {
    super(Response.status(Status.CONFLICT).build());
  }

  /**
   * Constructs a new ConflictException with the specified detail message.
   *
   * @param message the detail message
   */
  public ConflictException(String message) {
    super(Response.status(Status.CONFLICT).entity(message).type("text/plain").build());
  }

}
