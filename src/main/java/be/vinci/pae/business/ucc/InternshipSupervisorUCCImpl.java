package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.InternshipSupervisorDTO;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.InternshipSupervisorDAO;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response.Status;
import java.util.List;

/**
 * Represents an internship supervisor use case controller (UCC) with various methods.
 * This class defines methods for creating and retrieving internship supervisors.
 */
public class InternshipSupervisorUCCImpl implements InternshipSupervisorUCC {

  @Inject
  private InternshipSupervisorDAO internshipSupervisorDAO;

  @Inject
  private DALServices dalServices;


  /**
   * Create an internship supervisor.
   *
   * @param firstName The first name of the internship supervisor.
   * @param lastName The last name of the internship supervisor.
   * @param phoneNumber The phone number of the internship supervisor.
   * @param email The email of the internship supervisor.
   * @param company The company of the internship supervisor.
   *
   * @return The created internship supervisor.
   */
  @Override
  public InternshipSupervisorDTO createAnInternshipSupervisor(
      String firstName, String lastName, String phoneNumber, String email, int company) {
    dalServices.start();
    try {
      if (firstName == null || lastName == null || phoneNumber == null || company == 0) {
        dalServices.rollBack();
        throw new WebApplicationException("Missing information for internship supervisor.",
            Status.BAD_REQUEST);
      }

      InternshipSupervisorDTO supervisor = internshipSupervisorDAO.insertSupervisor(firstName,
          lastName, phoneNumber, email, company);
      dalServices.commit();

      System.out.println("superviso "
          + "r ucc : " + supervisor);

      return supervisor;
    } catch (Exception e) {
      dalServices.rollBack();
      throw new WebApplicationException("Failed to add internship supervisor.",
          Status.INTERNAL_SERVER_ERROR);
    }
  }


  /**
   * Retrieves all internship supervisors.
   *
   * @return A list of all internship supervisors.
   */
  @Override
  public List<InternshipSupervisorDTO> getAllInternshipSupervisors() {
    dalServices.start();
    try {
      List<InternshipSupervisorDTO> supervisors =
          internshipSupervisorDAO.getAllInternshipSupervisors();
      dalServices.commit();
      return supervisors;
    } catch (Exception e) {
      dalServices.rollBack();
      throw new WebApplicationException("Failed to retrieve internship supervisors.",
          Status.INTERNAL_SERVER_ERROR);
    }
  }
}
