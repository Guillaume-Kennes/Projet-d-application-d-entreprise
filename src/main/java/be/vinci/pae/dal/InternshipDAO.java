package be.vinci.pae.dal;

import be.vinci.pae.business.domain.InternshipDTO;
import java.sql.ResultSet;

/**
 * Represents a Data Access Object (DAO) for managing internship-related data.
 * Provides methods to retrieve internship information based on user or ID.
 */
public interface InternshipDAO {

  /**
   * Returns the internship corresponding to the given user.
   *
   * @param id the user's id
   *
   * @return the internship corresponding to the given user
   */
  InternshipDTO getInternshipByUserId(int id);

  /**
   * Returns the information of an internship.
   *
   * @param resultSet the given resultSet
   *
   * @return the internship corresponding to that result set
   */
  InternshipDTO internshipInfos(ResultSet resultSet);
}
