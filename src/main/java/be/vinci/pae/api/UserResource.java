package be.vinci.pae.api;

import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.business.ucc.UserUCC;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import org.apache.log4j.Logger;
import org.glassfish.jersey.server.ContainerRequest;

//JWT TOKENS ICI

@Singleton
@Path("/users")
public class UserResource {
  @Inject
  private UserUCC myUserUcc;

//  @Inject
//  private Logger logger;
//
//  @Path("/me")
//  @GET
//  @Produces(MediaType.APPLICATION_JSON)
//  @Authorize
//  public UserDTO getUserWithToken(@Context ContainerRequest request) {
//
//    UserDTO authenticatedUser = (UserDTO) request.getProperty("user");
//    logger.info("Token de " + authenticatedUser.getFirstName() + " " + authenticatedUser.getLastName());
//
//    return authenticatedUser;
//  }
//

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public UserDTO getUserById(@PathParam("id") int id){
    UserDTO user = myUserUcc.getUserById(id);
    if(user == null)
      throw new IllegalArgumentException("User not found");
    return user;
  }

}
