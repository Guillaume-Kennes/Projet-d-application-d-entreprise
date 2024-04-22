package be.vinci.pae.dal;

import be.vinci.pae.business.domain.InternshipDTO;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;


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
   * Inserts a new internship in the database.
   *
   * @return The inserted internship.
   */
  InternshipDTO createAnInternship(int contact, int supervisor, String projet, Date signatureDate);

  void update(InternshipDTO internship);

  InternshipDTO getInternshipById(int internshipId);



}
