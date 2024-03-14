package be.vinci.pae.utils.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

public class UnauthorizedException extends WebApplicationException {
  public UnauthorizedException() {

    super(Response.status(Status.UNAUTHORIZED).build());
  }

  public UnauthorizedException(String message) {
    super(Response.status(Status.UNAUTHORIZED).entity(message).type("text/plain").build());
  }

}
