package be.vinci.pae.api;

import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.business.domain.CompanyDTO;
import be.vinci.pae.business.ucc.CompanyUCC;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response.Status;
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
  @Authorize(value = {"Professeur"})
  public List<CompanyDTO> getAllEnterprises() {
    return companyUCC.getAllEnterprises();
  }


  /**
   * Adds a new contact.
   *
   * @param newCompanyDTO The contact data to be added.
   * @return The added contact data.
   */
  @POST
  @Path("/add")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public CompanyDTO addCompany(CompanyDTO newCompanyDTO) {
    System.out.println("CompanyResource -------> newCompanyDTO : " + newCompanyDTO);

    try {
      if (newCompanyDTO == null) {
        throw new WebApplicationException("Invalid company data", Status.BAD_REQUEST);
      }
      System.out.println("rentre ici 1");
      CompanyDTO addedCompanyDTO = companyUCC.addCompany(newCompanyDTO);
      System.out.println("CompanyResource ---> addedCompanyDTO : " + addedCompanyDTO);
      if (addedCompanyDTO == null) {
        System.out.println("rentre ici 2");
        throw new WebApplicationException("Company could not be added",
            Status.INTERNAL_SERVER_ERROR);
      }
      System.out.println(
          "CompanyResource ---> addedCompanyDTO tradeName : " + addedCompanyDTO.getTradeName());
      return addedCompanyDTO;
    } catch (Exception e) {
      System.out.println("ici ?");
      System.out.println("CompanyResource exception");
      throw new WebApplicationException("Failed to add contact", Status.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Get company by id.
   *
   * @param idCompany the id of the company
   * @return the company by id
   */
  @GET
  @Path("/numberOfStudentsTaken/{idCompany}")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(value = {"Professeur"})
  public int numberOfStudentsTaken(@PathParam("idCompany") int idCompany) {
    return companyUCC.numberOfStudentsTaken(idCompany);
  }

}