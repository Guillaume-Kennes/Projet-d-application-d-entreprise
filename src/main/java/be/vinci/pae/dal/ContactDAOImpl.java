package be.vinci.pae.dal;

import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.domain.DomainFactory;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementation of the ContactDAO interface.
 */
public class ContactDAOImpl implements ContactDAO {
  @Inject
  private DomainFactory myDomainFactory;

  @Inject
  private DALBackServices dalServices;

  /**
   * Retrieves a contact by its ID.
   *
   * @param contactId The ID of the contact to retrieve.
   *
   * @return The contact DTO if found, null otherwise.
   *
   * @throws IllegalArgumentException if the contact is not found.
   */
  public ContactDTO getContactById(int contactId) {

    PreparedStatement preparedStatement = dalServices.getPreparedStatement(
        "SELECT * FROM pae.contacts co WHERE co.id_contact = ?");
    try {
      preparedStatement.setInt(1, contactId);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          return contactInfos(resultSet);
        }
      }
    } catch (SQLException e) {
      throw new IllegalArgumentException("Contact not found");
    }
    return null;
  }


  /**
   * Extracts contact information from a ResultSet.
   *
   * @param resultSet The ResultSet to extract information from.
   *
   * @return A ContactDTO populated with the extracted information.
   */
  public ContactDTO contactInfos(ResultSet resultSet) {
    ContactDTO contactDTO = myDomainFactory.getContact();

    try {
      contactDTO.setId(resultSet.getInt("id_contact"));
      contactDTO.setState(resultSet.getString("state"));
      contactDTO.setEnterprise(resultSet.getInt("enterprise"));
      contactDTO.setInscriptionUE(resultSet.getInt("inscription_UE"));
      contactDTO.setReasonForRefusal(resultSet.getString("reason_for_refusal"));
      contactDTO.setFollowed(resultSet.getBoolean("is_followed"));
      contactDTO.setMeetingPlace(resultSet.getString("meeting_place"));
    } catch (SQLException e) {
      e.getMessage();
    }

    return contactDTO;
  }

  /**
   * Updates a contact in the database.
   *
   * @param contactDTO The contact DTO to update.
   *
   * @throws IllegalArgumentException if an SQL error occurs.
   */
  public void update(ContactDTO contactDTO) {
    try {
      String query = """
          UPDATE pae.contacts
          SET state = ?,
          enterprise = ?,
          inscription_ue = ?,
          reason_for_refusal = ?,
          is_followed = ?,
          meeting_place = ?
          WHERE id_contact= ?;
          """;
      try (PreparedStatement ps = dalServices.getPreparedStatement(query)) {
          ps.setString(1, contactDTO.getState());
          ps.setInt(2, contactDTO.getEnterprise());
          ps.setInt(3, contactDTO.getInscriptionUE());
          ps.setString(4, contactDTO.getReasonForRefusal());
          ps.setBoolean(5, contactDTO.isFollowed());
          ps.setString(6, contactDTO.getMeetingPlace());
          ps.setInt(7, contactDTO.getId());

          System.out.println("contact DAO IMPL : " + contactDTO.getId());

          ps.execute();
        }
    } catch (SQLException e) {
      throw new IllegalArgumentException(e);
    }
  }
}
