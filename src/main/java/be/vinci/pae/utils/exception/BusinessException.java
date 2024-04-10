package be.vinci.pae.utils.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;


/**
 * This exception represents a business exception HTTP status (400) in a web application. It
 * extends the WebApplicationException class and provides constructors to create instances of
 * BusinessException with or without a custom message.
 */
public class BusinessException extends WebApplicationException {

  /**
   * Constructs a new BusinessException with a default message.
   */
  public BusinessException() {
    super(Response.status(Status.BAD_REQUEST).build());
  }

  /**
   * Constructs a new BusinessException with the specified detail message.
   *
   * @param message the detail message (which is saved for
   *                later retrieval by the getMessage method).
   */
  public BusinessException(String message) {
    super(Response.status(Status.BAD_REQUEST).entity(message).type("text/plain").build());
  }

  /**
   * This exception represents a business exception in a web application
   *     with a specified HTTP status and custom message.
   * It extends the WebApplicationException class and provides a constructor
   *     to create instances of BusinessException with a custom message and HTTP status.
   */
  public BusinessException(String message, Status status) {
    super(Response.status(status).entity(message).type("text/plain").build());
  }
}
