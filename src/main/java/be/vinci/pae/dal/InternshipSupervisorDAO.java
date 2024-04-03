package be.vinci.pae.dal;

import be.vinci.pae.business.domain.InternshipSupervisorDTO;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represents a Data Access Object (DAO) for managing internship supervisor-related data.
 * Provides methods to retrieve supervisor information based on email or ID.
 */
public interface InternshipSupervisorDAO {

  /**
   * Returns the information of a supervisor.
   *
   * @param resultSet the given resultSet
   *
   * @return the supervisor corresponding to that result set
   */
  InternshipSupervisorDTO supervisorInfos(ResultSet resultSet);

  /**
   * Returns the supervisor corresponding to the id.
   *
   * @param id the supervisor's id
   *
   * @return the supervisor corresponding to the id
   */
  InternshipSupervisorDTO getSupervisorById(int id) throws SQLException;
}
