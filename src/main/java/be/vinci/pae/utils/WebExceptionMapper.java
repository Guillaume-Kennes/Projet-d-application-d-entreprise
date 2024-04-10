package be.vinci.pae.utils;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Exception mapper for handling uncaught exceptions thrown by the web application.
 * This class implements ExceptionMapper to provide custom handling for Throwable instances.
 */
@Provider
public class WebExceptionMapper implements ExceptionMapper<Throwable> {

  /**
   * Maps a Throwable to a Response object.
   *
   * @param exception The Throwable to map.
   *
   * @return A Response object representing the mapped exception.
   */
  @Override
  public Response toResponse(Throwable exception) {
    exception.printStackTrace();
    Logger log = AppLogger.getLogger(exception.getMessage());
    if (exception instanceof WebApplicationException) {
      log.log(Level.WARNING, exception.getMessage());
      return Response.status(((WebApplicationException) exception).getResponse().getStatus())
          .entity(exception.getMessage())
          .build();
    }
    log.log(Level.SEVERE, exception.getMessage());
    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
        .entity(exception.getMessage())
        .build();
  }
}