package be.vinci.pae.dal;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.InternshipDTO;
import be.vinci.pae.business.domain.InternshipSupervisorDTO;
import be.vinci.pae.utils.exception.FatalException;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the InternshipSupervisorDAO interface. Provides methods for retrieving
 * internship supervisor-related data from the database.
 */
public class InternshipSupervisorDAOImpl implements InternshipSupervisorDAO {

  @Inject
  private DomainFactory myDomainFactory;
  @Inject
  private DALBackServices dalServices;

  /**
   * Method to retrieve supervisor information and map it to a InternshipSupervisorDTO object.
   *
   * @param resultSet The ResultSet containing supervisor information.
   * @return A InternshipSupervisorDTO object populated with supervisor info.
   * @throws FatalException if the supervisor info is not found in the database.
   */
  public InternshipSupervisorDTO supervisorInfos(ResultSet resultSet) {
    InternshipSupervisorDTO internshipSupervisorDTO = myDomainFactory.getInternshipSupervisor();

    try {
      internshipSupervisorDTO.setId(resultSet.getInt("id_supervisor"));
      internshipSupervisorDTO.setEmail(resultSet.getString("email"));
      internshipSupervisorDTO.setPhoneNumber(resultSet.getString("phone_number"));
      internshipSupervisorDTO.setFirstName(resultSet.getString("supervisor_first_name"));
      internshipSupervisorDTO.setLastName(resultSet.getString("supervisor_last_name"));
      internshipSupervisorDTO.setVersionNumber(resultSet.getInt("version_internship_surpervisors"));
      internshipSupervisorDTO.setCompany(resultSet.getInt("enterprise"));
    } catch (SQLException e) {
      throw new FatalException(e);
    }
    return internshipSupervisorDTO;
  }

  /**
   * Method to retrieve a supervisor by their ID.
   *
   * @param id The ID of the supervisor to retrieve.
   * @return A InternshipSupervisorDTO object representing the supervisor, or null if not found.
   * @throws FatalException if the supervisor is not found in the database.
   */
  public InternshipSupervisorDTO getSupervisorById(int id) throws SQLException {
    PreparedStatement preparedStatement = dalServices.getPreparedStatement(
        "SELECT * FROM pae.internship_supervisors s, pae.enterprises e"
            + " WHERE s.enterprise = e.id_enterprise AND s.id_supervisor = ?");
    try {
      preparedStatement.setInt(1, id);
    } catch (SQLException e) {
      throw new FatalException(e);
    }

    InternshipSupervisorDTO supervisor = myDomainFactory.getInternshipSupervisor();
    try (ResultSet resultSet = preparedStatement.executeQuery()) {
      if (resultSet.next()) {
        supervisor = supervisorInfos(resultSet);
      } else {
        supervisor = null;
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    } finally {
      preparedStatement.close();
    }
    return supervisor;
  }

  /**
   * Method to insert a new supervisor in the database.
   *
   * @param firstName The first name of the supervisor to insert.
   * @param lastName The last name of the supervisor to insert.
   * @param phoneNumber The phone number of the supervisor to insert.
   * @param email The email of the supervisor to insert.
   * @param company The company of the supervisor to insert.
   * @return The InternshipSupervisorDTO object representing the newly inserted supervisor.
   */
  public InternshipSupervisorDTO insertSupervisor(
      String firstName, String lastName, String phoneNumber, String email, int company) {
    try {
      String query = """
          INSERT INTO pae.internship_supervisors (
          enterprise,
          supervisor_last_name,
          supervisor_first_name,
          phone_number,
          email,
          version_internship_surpervisors)
          VALUES (?, ?, ?, ?, ?, ?)
          """;

      try (PreparedStatement ps = dalServices.getPreparedStatement(query)) {
        ps.setInt(1, company);
        ps.setString(2, lastName);
        ps.setString(3, firstName);
        ps.setString(4, phoneNumber);
        ps.setString(5, email);
        ps.setInt(6, 1);

        ps.execute();
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }
    System.out.println("InternshipSUpervisor insert : " + firstName + " " + lastName
        + " " + phoneNumber + " " + email + " " + company);
    return myDomainFactory.getInternshipSupervisor();
  }

  /**
   * Method to retrieve all internship supervisors from the database.
   *
   * @return A list of InternshipSupervisorDTO objects representing all supervisors.
   */
  public List<InternshipSupervisorDTO> getAllInternshipSupervisors() {
    List<InternshipSupervisorDTO> supervisorsList = new ArrayList<>();
    PreparedStatement preparedStatement = dalServices.getPreparedStatement(
        "SELECT * FROM pae.internship_supervisors");
    try (ResultSet resultSet = preparedStatement.executeQuery()) {
      while (resultSet.next()) {
        InternshipSupervisorDTO supervisor = myDomainFactory.getInternshipSupervisor();
        supervisor.setId(resultSet.getInt("id_supervisor"));
        supervisor.setEmail(resultSet.getString("email"));
        supervisor.setPhoneNumber(resultSet.getString("phone_number"));
        supervisor.setFirstName(resultSet.getString("supervisor_first_name"));
        supervisor.setLastName(resultSet.getString("supervisor_last_name"));
        supervisor.setVersionNumber(resultSet.getInt("version_internship_surpervisors"));
        supervisor.setCompany(resultSet.getInt("enterprise"));
        supervisorsList.add(supervisor);
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }
    return supervisorsList;
  }

  /**
   * Retrieves the internship supervisor information associated with the specified ID.
   *
   * @param internshipId The ID of the internship supervisor to retrieve information for.
   * @return An InternshipSupervisorDTO object representing the internship supervisor information.
   */
  public InternshipDTO getInternshipById(int internshipId) {
    PreparedStatement preparedStatement = dalServices.getPreparedStatement(
        "SELECT * FROM pae.internships WHERE id_internship = ?");
    try {
      preparedStatement.setInt(1, internshipId);
    } catch (SQLException e) {
      throw new FatalException(e);
    }

    InternshipDTO internship = myDomainFactory.getInternship();
    try (ResultSet resultSet = preparedStatement.executeQuery()) {
      if (resultSet.next()) {
        internship.setId(resultSet.getInt("id_internship"));
        internship.setContact(resultSet.getInt("contact"));
        internship.setSupervisor(resultSet.getInt("supervisor"));
        internship.setProject(resultSet.getString("project"));
        internship.setSignatureDate(resultSet.getDate("signature_date"));
        internship.setVersionNumber(resultSet.getInt("version_internships"));
      } else {
        internship = null;
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    } finally {
      try {
        preparedStatement.close();
      } catch (SQLException e) {
        throw new FatalException(e);
      }
    }
    return internship;
  }
}