package be.vinci.pae.dal;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.UEInscriptionDTO;
import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.utils.exception.FatalException;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementation of the ViewUEInscriptionDAO interface. Provides methods for retrieving UE
 * inscription-related data from the database.
 */
public class UEInscriptionDAOImpl implements UEInscriptionDAO {

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
   * @return A ViewUEInscriptionDTO object populated with UE inscription info.
   * @throws FatalException if the inscription info is not found in the database.
   */
  public UEInscriptionDTO ueInscriptionInfos(ResultSet resultSet) {
    UEInscriptionDTO inscription = myDomainFactory.getUEInscription();
    UserDTO student;

    try {
      inscription.setId(resultSet.getInt("id_inscription_UE"));
      inscription.setSchoolYear(resultSet.getString("school_year"));
      student = userDAO.userInfos(resultSet);
      inscription.setStudent((User) student);
    } catch (SQLException e) {
      throw new FatalException(e);
    }

    return inscription;
  }

  /**
   * Method to retrieve a UE inscription by its ID.
   *
   * @param id The ID of the UE inscription to retrieve.
   * @return A ViewUEInscriptionDTO object representing the inscription, or null if not found.
   * @throws FatalException if the inscription is not found in the database.
   */
  public UEInscriptionDTO getUeInscriptionById(int id) throws SQLException {
    PreparedStatement preparedStatement = dalServices.getPreparedStatement(
        "SELECT * FROM pae.users u, pae.inscriptions_UE i"
            + " WHERE i.student = u.id_user AND i.id_inscription_UE = ?");
    try {
      preparedStatement.setInt(1, id);
    } catch (SQLException e) {
      throw new FatalException(e);
    }

    UEInscriptionDTO inscription = myDomainFactory.getUEInscription();
    try (ResultSet resultSet = preparedStatement.executeQuery()) {
      if (resultSet.next()) {
        inscription = ueInscriptionInfos(resultSet);
      } else {
        inscription = null;
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    } finally {
        preparedStatement.close();
    }
    return inscription;
  }
}