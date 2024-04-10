package be.vinci.pae.utils.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

/**
 * Custom exception class representing a fatal error in the web application.
 * It inherits from the WebApplicationException class in order to deliver
 *     an HTTP response with a status code of 500 (Internal Server Error)
 * along with an optional message clarifying the error encountered.
 */
public class FatalException extends WebApplicationException {

  /**
   * Constructs a new FatalException with a default error response HTTP 500 Internal Server Error.
   */
  public FatalException() {
    super(Response.status(Status.INTERNAL_SERVER_ERROR).build());
  }

  /**
   * Constructs a new FatalException with the specified message and a status code of 500.
   *
   * @param message the error message that explains the reason for the exception.
   */
  public FatalException(String message) {
    super(Response.status(Status.INTERNAL_SERVER_ERROR).entity(message).type("text/plain").build());
  }

  /**
   * Constructs a new FatalException with the specified cause and a status code of 500.
   *
   * @param cause the Throwable that caused this exception to be thrown.
   */
  public FatalException(Throwable cause) {
    super(
        Response.status(Status.INTERNAL_SERVER_ERROR).entity(cause.getMessage()).type("text/plain")
            .build());
  }

  /**
   * Constructs a new FatalException with the specified error message and HTTP status.
   * This constructor creates a new FatalException with the given error message and
   * HTTP status. It constructs a Response object using the provided status and message,
   * and passes it to the superclass constructor of Throwable.
   *
   * @param message The error message associated with the exception.
   *
   * @param status The HTTP status code associated with the exception.
   */
  public FatalException(String message, Status status) {
    super(Response.status(status).entity(message).type("text/plain").build());
  }


}

