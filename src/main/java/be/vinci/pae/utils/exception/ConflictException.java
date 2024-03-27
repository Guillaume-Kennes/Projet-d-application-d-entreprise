package be.vinci.pae.utils.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

/**
 * This exception represents a conflict HTTP status (409) in a web application.
 * It extends the WebApplicationException class and provides
 *     constructors to create instances of ConflictException
 *     with or without a custom message.
 */
public class ConflictException extends WebApplicationException {

  /**
   * Constructs a new ConflictException with a default message.
   */
  public ConflictException() {
    super(Response.status(Status..CONFLICT).build());
  }

  /**
   * Constructs a new ConflictException with the specified detail message.
   *
   * @param message the detail message
   *                (which is saved for later retrieval by the getMessage
   *                method).
   */
  public ConflictException(String message) {
    super(Response.status(Status.CONFLICT).entity(message).type("text/plain").build());
  }

}
