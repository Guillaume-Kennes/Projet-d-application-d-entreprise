package be.vinci.pae.dal;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.business.domain.ViewUEInscriptionDTO;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementation of the ViewUEInscriptionDAO interface.
 * Provides methods for retrieving UE inscription-related data from the database.
 */
public class ViewUEInscriptionDAOImpl implements ViewUEInscriptionDAO {

  @Inject
  private DomainFactory myDomainFactory;
  @Inject
  private DALBackServices dalServices;
  @Inject
  private UserDAO userDAO;

  /**
   * Method to retrieve UE Inscription info and map it to a ViewUEInscriptionDTO object.
   *
   * @param resultSet The ResultSet containing inscription information.
   *
   * @return A ViewUEInscriptionDTO object populated with UE inscription info.
   */
  public ViewUEInscriptionDTO ueInscriptionInfos(ResultSet resultSet) {
    ViewUEInscriptionDTO inscription = myDomainFactory.getUEInscription();
    UserDTO student;

    try {
      inscription.setId(resultSet.getInt("id_inscription_ue"));
      inscription.setSchoolYear(resultSet.getString("school_year"));
      student = userDAO.userInfos(resultSet);
      inscription.setStudent((User) student);
    } catch (SQLException e) {
      e.getMessage();
    }

    return inscription;
  }

  /**
   * Method to retrieve a UE inscription by its ID.
   *
   * @param id The ID of the UE inscription to retrieve.
   *
   * @return A ViewUEInscriptionDTO object representing the inscription, or null if not found.
   *
   * @throws IllegalArgumentException if the inscription is not found in the database.
   */
  public ViewUEInscriptionDTO getUeInscriptionById(int id) {
    PreparedStatement preparedStatement = dalServices.getPreparedStatement(
        "SELECT * FROM pae.users u, pae.inscriptions_ue i"
            + " WHERE i.student = u.id_user AND i.id_inscription_ue = ?");
    try {
      preparedStatement.setInt(1, id);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    ViewUEInscriptionDTO inscription = myDomainFactory.getUEInscription();
    try (ResultSet resultSet = preparedStatement.executeQuery()) {
      if (resultSet.next()) {
        inscription = ueInscriptionInfos(resultSet);
      } else {
        inscription = null;
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
    return inscription;
  }
}
