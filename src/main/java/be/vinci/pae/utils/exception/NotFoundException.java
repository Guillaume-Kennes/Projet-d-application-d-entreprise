package be.vinci.pae.utils.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

/**
 * Custom exception class representing a "Not Found" error (HTTP 404).
 * Extends WebApplicationException.
 */
public class NotFoundException extends WebApplicationException {

  /**
   * Constructs a new NotFoundException with a default message and HTTP status code 404.
   */
  public NotFoundException() {
    super(Response.status(Status.NOT_FOUND).build());
  }

  /**
   * Constructs a new NotFoundException with a custom message and HTTP status code 404.
   *
   * @param message The detail message. This message is used for response entity.
   */
  public NotFoundException(String message) {
    super(Response.status(Status.NOT_FOUND).entity(message).type("text/plain").build());
  }
}
