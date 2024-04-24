package be.vinci.pae.dal;

import be.vinci.pae.business.domain.InternshipDTO;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 * Represents a Data Access Object (DAO) for managing internship-related data. Provides methods to
 * retrieve internship information based on user or ID.
 */
public interface InternshipDAO {

  /**
   * Retrieves the internship associated with a user identified by their ID.
   *
   * @param id The ID of the user for whom to retrieve the internship.
   * @return An InternshipDTO object representing the internship associated with the user.
   * @throws SQLException if an SQL exception occurs during the retrieval process.
   */
  InternshipDTO getInternshipByUserId(int id) throws SQLException;

  /**
   * Returns the information of an internship.
   *
   * @param resultSet the given resultSet
   * @return the internship corresponding to that result set
   */
  InternshipDTO internshipInfos(ResultSet resultSet);


  /**
   * Create an internship.
   *
   * @param contact the contact of the internship
   * @param supervisor the supervisor of the internship
   * @param projet the project of the internship
   * @param signatureDate the signature date of the internship
   * @return the created internship
   */
  InternshipDTO createAnInternship(int contact, int supervisor, String projet, Date signatureDate);

  /**
   * Updates an internship in the database.
   *
   * @param internship the internship to update
   */
  void update(InternshipDTO internship);

  /**
   * Retrieves the internship associated with the specified ID.
   *
   * @param internshipId The ID of the internship to retrieve information for.
   * @return An InternshipDTO object representing the internship information.
   */
  InternshipDTO getInternshipById(int internshipId);

  /**
   * Retrieves all internships.
   *
   * @return A list of all internships.
   */
  List<InternshipDTO> getAllInternships();

}
