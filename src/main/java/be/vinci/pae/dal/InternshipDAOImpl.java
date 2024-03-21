package be.vinci.pae.dal;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.InternshipDTO;
import be.vinci.pae.business.domain.InternshipSupervisor;
import be.vinci.pae.business.domain.UserDTO;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementation of the InternshipDAO interface.
 * Provides methods for retrieving internship-related data from the database.
 */
public class InternshipDAOImpl implements InternshipDAO{

  @Inject
  private DomainFactory myDomainFactory;

  @Inject
  private DALBackServices dalServices;

  /**
   * Retrieves an internship by their user id from the database.
   *
   * @param id The id of the user whose internship to retrieve.
   *
   * @return an internship corresponding to the specified user, or null if not found.
   *
   * @throws RuntimeException if an SQL exception occurs while accessing the database.
   */
  public InternshipDTO getInternshipByUserId(int id) {

    PreparedStatement preparedStatement = dalServices.getPreparedStatement(
        "SELECT * FROM pae.internships i, pae.contacts c, pae.users u, pae.inscriptions_UE iu "
            + "WHERE i.contact = c.id_contact AND c.inscription_UE = iu.id_inscription_UE "
            + "AND iu.student = u.id_user AND u.id_user = ?");
    try {
      preparedStatement.setInt(1, id);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return null;
  }

  /**
   * Method to retrieve internship information from a ResultSet and map it to a InternshipDTO object.
   *
   * @param resultSet The ResultSet containing internship information.
   *
   * @return A InternshipDTO object populated with internship information from the ResultSet.
   */
  public InternshipDTO internshipInfos(ResultSet resultSet) {
    InternshipDTO internshipDTO = myDomainFactory.getInternship();

    try {
      internshipDTO.setId(resultSet.getInt("id_internship"));
      internshipDTO.setProject(resultSet.getString("internship_project"));
      internshipDTO.setDate(resultSet.getString("signature_date"));
      int idSupervisor = resultSet.getInt("internship_supervisor");
    } catch (SQLException e) {
      e.getMessage();
    }
    return internshipDTO;
  }


}
