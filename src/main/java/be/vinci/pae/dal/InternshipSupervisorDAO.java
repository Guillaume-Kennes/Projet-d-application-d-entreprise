package be.vinci.pae.dal;

import be.vinci.pae.business.domain.InternshipDTO;
import be.vinci.pae.business.domain.InternshipSupervisorDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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



  /**
   * Inserts a new supervisor in the database.
   *
   * @param firstName The first name of the supervisor to insert.
   * @param lastName The last name of the supervisor to insert.
   * @param phoneNumber The phone number of the supervisor to insert.
   * @param email The email of the supervisor to insert.
   * @param company The company of the supervisor to insert.
   * @return The InternshipSupervisorDTO object representing the newly inserted supervisor.
   */
  InternshipSupervisorDTO insertSupervisor(String firstName,
      String lastName, String phoneNumber, String email, int company);

  List<InternshipSupervisorDTO> getAllInternshipSupervisors();

  InternshipDTO getInternshipById(int internshipId);

}
