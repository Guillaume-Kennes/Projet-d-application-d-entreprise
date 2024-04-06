package be.vinci.pae.api.filters;

import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.business.ucc.UserUCC;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;

@Singleton
@Provider
@isTeacher
@Priority(2)
public class isTeacherRequestFilter implements ContainerRequestFilter {

  @Inject
  private UserUCC userUCC;


  @Override
  public void filter(@Context ContainerRequestContext requestContext) throws IOException {

    UserDTO authenticatedUser = (UserDTO) requestContext.getProperty("user");
    if (!userUCC.userIsTeacher(authenticatedUser)) {
      requestContext.abortWith(Response.status(Status.FORBIDDEN)
          .entity("You are forbidden to access this resource").build());
    }
  }
}
