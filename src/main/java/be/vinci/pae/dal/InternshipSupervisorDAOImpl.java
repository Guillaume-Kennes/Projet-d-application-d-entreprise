package be.vinci.pae.dal;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.InternshipSupervisorDTO;
import be.vinci.pae.business.domain.Company;
import be.vinci.pae.business.domain.CompanyDTO;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementation of the InternshipSupervisorDAO interface.
 * Provides methods for retrieving internship supervisor-related data from the database.
 */
public class InternshipSupervisorDAOImpl implements InternshipSupervisorDAO {

  @Inject
  private DomainFactory myDomainFactory;
  @Inject
  private DALBackServices dalServices;
  @Inject
  private CompanyDAO companyDAO;

  /**
   * Method to retrieve supervisor information and map it to a InternshipSupervisorDTO object.
   *
   * @param resultSet The ResultSet containing supervisor information.
   *
   * @return A InternshipSupervisorDTO object populated with supervisor info.
   */
  public InternshipSupervisorDTO supervisorInfos(ResultSet resultSet) {
    InternshipSupervisorDTO internshipSupervisorDTO = myDomainFactory.getInternshipSupervisor();
    CompanyDTO company;

    try {
      internshipSupervisorDTO.setId(resultSet.getInt("id_supervisor"));
      internshipSupervisorDTO.setEmail(resultSet.getString("email"));
      internshipSupervisorDTO.setPhoneNumber(resultSet.getString("phone_number"));
      internshipSupervisorDTO.setFirstName(resultSet.getString("supervisor_first_name"));
      internshipSupervisorDTO.setLastName(resultSet.getString("supervisor_last_name"));
      company = companyDAO.companyInfos(resultSet);
      internshipSupervisorDTO.setCompany((Company) company);
    } catch (SQLException e) {
      e.getMessage();
    }

    return internshipSupervisorDTO;
  }

  /**
   * Method to retrieve a supervisor by their ID.
   *
   * @param id The ID of the supervisor to retrieve.
   *
   * @return A InternshipSupervisorDTO object representing the supervisor, or null if not found.
   *
   * @throws IllegalArgumentException if the supervisor is not found in the database.
   */
  public InternshipSupervisorDTO getSupervisorById(int id) {
    PreparedStatement preparedStatement = dalServices.getPreparedStatement(
        "SELECT * FROM pae.internship_supervisors s, pae.enterprises e"
            + " WHERE s.enterprise = e.id_enterprise AND s.id_supervisor = ?");
    try {
      preparedStatement.setInt(1, id);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    InternshipSupervisorDTO supervisor = myDomainFactory.getInternshipSupervisor();
    try (ResultSet resultSet = preparedStatement.executeQuery()) {
      if (resultSet.next()) {
        supervisor = supervisorInfos(resultSet);
      } else {
        supervisor = null;
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
      System.exit(1);
    } finally {
      try {
        preparedStatement.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return supervisor;
  }
}
