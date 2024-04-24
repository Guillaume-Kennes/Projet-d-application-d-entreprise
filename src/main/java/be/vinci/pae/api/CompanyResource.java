package be.vinci.pae.api;

import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.business.domain.CompanyDTO;
import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.ucc.CompanyUCC;
import be.vinci.pae.business.ucc.ContactUCC;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * The Class CompanyResource.
 */
@Singleton
@Path("/companies")
public class CompanyResource {

  private final ObjectMapper jsonMapper = new ObjectMapper();
  @Inject
  private CompanyUCC companyUCC;
  @Inject
  private ContactUCC contactUCC;

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

  /**
   * Retrieves contacts associated with a company by its ID.
   *
   * @param id The ID of the company.
   * @return An ObjectNode containing the contacts associated with the company, formatted as JSON.
   * @throws SQLException if an SQL exception occurs during the retrieval process.
   */
  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(value = {"Professeur"})
  public ObjectNode getContactsByCompanyId(@PathParam("id") int id) throws SQLException {
    ObjectNode response = jsonMapper.createObjectNode();
    ArrayList<ContactDTO> contacts = contactUCC.getAllContacts(id);

    if (contacts.isEmpty()) {
      return null;
    }

    ArrayList<String> returnThing = new ArrayList<>();
    for (ContactDTO c : contacts) {
      if (c.getReasonForRefusal() != null) {
        returnThing.add("Contact avec l'étudiant "
            + c.getInscriptionUE().getStudent().getFirstName() + " "
            + c.getInscriptionUE().getStudent().getLastName() + " en "
            + c.getInscriptionUE().getSchoolYear() + "\n"
            + "Contact refusé pour la raison suivante : "
            + c.getReasonForRefusal());
      } else {
        returnThing.add("Contact avec l'étudiant "
            + c.getInscriptionUE().getStudent().getFirstName() + " "
            + c.getInscriptionUE().getStudent().getLastName() + " en "
            + c.getInscriptionUE().getSchoolYear());
      }
    }
    response.putPOJO("contacts", returnThing);
    return response;
  }
}