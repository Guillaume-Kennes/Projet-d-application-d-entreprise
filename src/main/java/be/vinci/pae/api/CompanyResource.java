package be.vinci.pae.api;

import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.business.domain.CompanyDTO;
import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.ucc.CompanyUCC;
import be.vinci.pae.business.ucc.ContactUCC;
import be.vinci.pae.utils.AppLogger;
import com.fasterxml.jackson.databind.JsonNode;
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
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The Class CompanyResource.
 */
@Singleton
@Path("/companies")
public class CompanyResource {

  @Inject
  private CompanyUCC companyUCC;
  @Inject
  private ContactUCC contactUCC;
  private Logger log;

  /**
   * Get all enterprises.
   *
   * @return the list of all enterprises
   */
  @GET
  @Path("/getEnterprises")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(value = {"Professeur", "Etudiant", "Administratif"})
  public List<CompanyDTO> getAllEnterprises() {
    return companyUCC.getAllEnterprises();
  }

  /**
   * Get all enterprises for a given school year.
   *
   * @param schoolYear the school year
   * @return the list of all enterprises for the given school year
   */
  @GET
  @Path("/getEnterprises/{schoolYear}")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(value = {"Professeur", "Etudiant", "Administratif"})
  public List<CompanyDTO> getAllEnterprises(@PathParam("schoolYear") String schoolYear) {
    return companyUCC.getAllEnterprises(schoolYear);
  }

  /**
   * Adds a new company.
   *
   * @param newCompanyDTO The company data to be added.
   * @return The added company data.
   */
  @POST
  @Path("/add")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Authorize(value = {"Etudiant"})
  public CompanyDTO addCompany(CompanyDTO newCompanyDTO) {
    System.out.println("CompanyResource -------> newCompanyDTO : " + newCompanyDTO);

    try {
      if (newCompanyDTO == null) {
        throw new WebApplicationException("Invalid company data", Status.BAD_REQUEST);
      }

      CompanyDTO addedCompanyDTO = companyUCC.addCompany(newCompanyDTO);

      if (addedCompanyDTO == null) {
        throw new WebApplicationException("Company could not be added",
            Status.INTERNAL_SERVER_ERROR);
      }
      log = AppLogger.getLogger("Ajout d'une entreprise");
      log.log(Level.FINE, "Ajout de l'entreprise " + addedCompanyDTO.getTradeName() + " "
       + addedCompanyDTO.getDesignation());

      return addedCompanyDTO;

    } catch (Exception e) {
      throw new WebApplicationException("Failed to add contact", Status.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Get the number of students taken by a company.
   *
   * @param idCompany the id of the company
   * @return the number of students
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
   * @return An array containing the contacts associated with the company.
   * @throws SQLException if an SQL exception occurs during the retrieval process.
   */
  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(value = {"Professeur", "Administratif"})
  public ArrayList<ContactDTO> getContactsByCompanyId(@PathParam("id") int id) throws SQLException {
    ArrayList<ContactDTO> contacts = contactUCC.getAllContacts(id);

    if (contacts.isEmpty()) {
      return null;
    }

    for (ContactDTO c : contacts) {
      System.out.println(c.getInscriptionUE().getStudent().getLastName());
    }

    log = AppLogger.getLogger("Demande de contacts pour une entreprise");
    log.log(Level.FINE, "Demande de visualisation de tous les contacts"
        + " de l'entreprise " + companyUCC.getCompanyById(id).getTradeName());

    return contacts;
  }

  /**
   * Blacklists a company with the specified ID, providing a reason for blacklisting.
   *
   * @param idCompany The ID of the company to blacklist.
   * @param json      A JsonNode object containing the reason for blacklisting.
   * @return A CompanyDTO object representing the blacklisted company.
   * @throws IllegalArgumentException if the company with the specified ID is not found, or if the
   *                                  request body is missing or not a valid JSON.
   */
  @POST
  @Path("/blacklist/{id_com}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(value = {"Professeur"})
  public CompanyDTO blacklist(@PathParam("id_com") int idCompany, JsonNode json) {
    CompanyDTO companyDTO = companyUCC.getCompanyById(idCompany);
    if (companyDTO == null) {
      throw new IllegalArgumentException("Company not found");
    }

    if (json == null) {
      throw new IllegalArgumentException("Request body is missing or not a valid JSON");
    }

    String reasonForRefusal = json.get("reasonBlackList").asText();
    companyUCC.blackList(companyDTO, reasonForRefusal);

    log = AppLogger.getLogger("Blacklisting d'une entreprise");
    log.log(Level.FINE, "Blacklisting de l'entreprise " + companyDTO.getTradeName()
     + " pour la raison suivante : " + reasonForRefusal);

    return companyDTO;
  }
}