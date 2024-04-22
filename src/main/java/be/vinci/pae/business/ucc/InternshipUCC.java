package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.InternshipDTO;
import java.sql.Date;
import java.sql.SQLException;


/**
 * Represents an Internship Use Case Controller (UCC) with methods related to internship
 * operations.
 */
public interface InternshipUCC {

  /**
   * Retrieves the internship associated with a user identified by their ID.
   *
   * @param id The ID of the user for whom to retrieve the internship.
   *
   * @return An InternshipDTO object representing the internship associated with the user.
   *
   * @throws SQLException if an SQL exception occurs during the retrieval process.
   */
  InternshipDTO getInternshipByUserId(int id) throws SQLException;


  /**
   * Create an internship.
   *
   *
   * @return The created internship.
   */
  InternshipDTO createAnInternship(int contact, int supervisor, String projet, Date signatureDate);

  InternshipDTO createOrModifyAnInternship(InternshipDTO internship, String subject);

  InternshipDTO getInternshipById(int internshipId);

}
