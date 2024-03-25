package be.vinci.pae.dal;

import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.InternshipDTO;
import be.vinci.pae.business.domain.InternshipSupervisor;
import be.vinci.pae.business.domain.InternshipSupervisorDTO;
import be.vinci.pae.business.domain.ViewContact;
import be.vinci.pae.business.domain.ViewContactDTO;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementation of the InternshipDAO interface.
 * Provides methods for retrieving internship-related data from the database.
 */
public class InternshipDAOImpl implements InternshipDAO {

  @Inject
  private DomainFactory myDomainFactory;

  @Inject
  private DALBackServices dalServices;
  @Inject
  private ViewContactDAO contactDAO;
  @Inject
  private InternshipSupervisorDAO supervisorDAO;

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
        "SELECT * FROM pae.internships i, pae.contacts c, pae.users u, "
            + "pae.inscriptions_UE iu, pae.internship_supervisors s "
            + "WHERE i.contact = c.id_contact AND c.inscription_UE = iu.id_inscription_UE "
            + "AND iu.student = u.id_user AND "
            + "i.internship_supervisor = s.id_supervisor AND u.id_user = ?");
    try {
      preparedStatement.setInt(1, id);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    InternshipDTO internship = myDomainFactory.getInternship();
    try (ResultSet resultSet = preparedStatement.executeQuery()) {
      if (resultSet.next()) {
        internship = internshipInfos(resultSet);
      } else {
        internship = null;
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
    return internship;
  }

  /**
   * Method to retrieve internship information and map it to an InternshipDTO object.
   *
   * @param resultSet The ResultSet containing internship information.
   *
   * @return An InternshipDTO object populated with internship information.
   */
  public InternshipDTO internshipInfos(ResultSet resultSet) {
    InternshipDTO internshipDTO = myDomainFactory.getInternship();
    ContactDTO contact;
    InternshipSupervisorDTO supervisor;

    try {
      internshipDTO.setId(resultSet.getInt("id_internship"));
      internshipDTO.setProject(resultSet.getString("internship_project"));
      internshipDTO.setDate(String.valueOf(resultSet.getDate("signature_date")));
      contact = contactDAO.contactInfos(resultSet);
      internshipDTO.setContact((ViewContact) contact);
      supervisor = supervisorDAO.supervisorInfos(resultSet);
      internshipDTO.setSupervisor((InternshipSupervisor) supervisor);
    } catch (SQLException e) {
      e.getMessage();
    }
    return internshipDTO;
  }


}
