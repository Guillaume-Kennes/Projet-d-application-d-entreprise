package be.vinci.pae.api;

import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.business.domain.CompanyDTO;
import be.vinci.pae.business.ucc.CompanyUCC;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

/**
 * The Class CompanyResource.
 */
@Singleton
@Path("/companies")
public class CompanyResource {

  @Inject
  private CompanyUCC companyUCC;

  /**
   * Get all enterprises.
   *
   * @return the list of all enterprises
   */
  @GET
  @Path("/getEnterprises")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize (value = {"Professeur"})
  public List<CompanyDTO> getAllEnterprises() {
    return companyUCC.getAllEnterprises();
  }
}