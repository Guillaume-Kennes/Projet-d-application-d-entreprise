package be.vinci.pae.api.filters;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

/**
 * The TokenDecodingException class extends the WebApplicationException class. It is thrown when
 * there is an error in decoding the token.
 */
public class TokenDecodingException extends WebApplicationException {

  /**
   * This constructor creates a new instance of TokenDecodingException without an error message. It
   * constructs an HTTP response with the UNAUTHORIZED status (401).
   */
  public TokenDecodingException() {
    super(Response.Status.UNAUTHORIZED);
  }

  /**
   * Constructs a new TokenDecodingException with the specified detail message.
   *
   * @param message the detail message (which is saved for later retrieval by the
   *                Throwable.getMessage() method).
   */
  public TokenDecodingException(String message) {
    super(message, Response.Status.UNAUTHORIZED);
  }

  /**
   * Constructs a new TokenDecodingException with the specified cause.
   *
   * @param cause the cause (which is saved for later retrieval by the Throwable.getCause()
   *              method).
   */
  public TokenDecodingException(Throwable cause) {
    super(cause.getMessage(), Response.Status.UNAUTHORIZED);
  }
}