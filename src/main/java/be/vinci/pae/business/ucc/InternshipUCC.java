package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.InternshipDTO;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;


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
   * @param contact The contact of the internship.
   * @param supervisor The supervisor of the internship.
   * @param projet The project of the internship.
   * @param signatureDate The signature date of the internship.
   *
   * @return The created internship.
   */
  InternshipDTO createAnInternship(int contact, int supervisor, String projet, Date signatureDate);

  /**
   * Create or modify an internship.
   *
   * @param internship The internship to create or modify.
   * @param subject The subject of the internship.
   *
   * @return The created or modified internship.
   */
  InternshipDTO createOrModifyAnInternship(InternshipDTO internship, String subject);

  /**
   * Retrieves the internship associated with the specified ID.
   *
   * @param internshipId The ID of the internship to retrieve information for.
   *
   * @return An InternshipDTO object representing the internship information.
   */
  InternshipDTO getInternshipById(int internshipId);

  /**
   * Retrieves all internships.
   *
   * @return A list of all internships.
   */
  List<InternshipDTO> getAllInternships();

  List<String> getSchoolYears();
}
