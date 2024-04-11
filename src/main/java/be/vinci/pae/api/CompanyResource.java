package be.vinci.pae.api;

import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.business.domain.CompanyDTO;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.business.ucc.CompanyUCC;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Singleton
@Path("/companies")
public class CompanyResource {

  @Inject
  private CompanyUCC companyUCC;

  /**
   * Get all enterprises.
   *
   * @param requestContext the request context
   * @return the list of all enterprises
   */
  @GET
  @Path("/getEntreprises")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize (value = {"Professeur"})
  public List<CompanyDTO> getAllEnterprises(@Context ContainerRequestContext requestContext) {
    UserDTO authentificatedUser = (UserDTO) requestContext.getProperty("user");
    System.out.println(authentificatedUser); //juste pour Jenkins
    return companyUCC.getAllEnterprises();
  }
}
