package be.vinci.pae.dal;

import be.vinci.pae.business.domain.InternshipSupervisorDTO;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represents a Data Access Object (DAO) for managing internship supervisor-related data. Provides
 * methods to retrieve supervisor information based on email or ID.
 */
public interface InternshipSupervisorDAO {

  /**
   * Returns the information of a supervisor.
   *
   * @param resultSet the given resultSet
   * @return the supervisor corresponding to that result set
   */
  InternshipSupervisorDTO supervisorInfos(ResultSet resultSet);

  /**
   * Retrieves the internship supervisor information associated with the specified ID.
   *
   * @param id The ID of the internship supervisor to retrieve information for.
   * @return An InternshipSupervisorDTO object representing the internship supervisor information.
   * @throws SQLException if an SQL exception occurs during the retrieval process.
   */
  InternshipSupervisorDTO getSupervisorById(int id) throws SQLException;
}
