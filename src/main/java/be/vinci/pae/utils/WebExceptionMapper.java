package be.vinci.pae.utils;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

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
      if (exception instanceof WebApplicationException) {
        return Response.status(((WebApplicationException) exception).getResponse().getStatus())
            .entity(exception.getMessage())
            .build();
      }
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity(exception.getMessage())
          .build();
    }
  }