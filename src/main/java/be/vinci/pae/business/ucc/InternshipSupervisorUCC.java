package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.InternshipSupervisorDTO;
import java.util.List;

/**
 * Represents an internship supervisor use case controller (UCC) with various methods.
 * This interface defines methods for creating and retrieving internship supervisors.
 */
public interface InternshipSupervisorUCC {


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
  InternshipSupervisorDTO createAnInternshipSupervisor(String firstName,
      String lastName, String phoneNumber, String email, int company);

  List<InternshipSupervisorDTO> getAllInternshipSupervisors();
}

