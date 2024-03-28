package be.vinci.pae.dal;

import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.ViewCompany;
import be.vinci.pae.business.domain.ViewCompanyDTO;
import be.vinci.pae.business.domain.ViewUEInscription;
import be.vinci.pae.business.domain.ViewUEInscriptionDTO;
import be.vinci.pae.utils.exception.FatalException;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response.Status;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Implementation of the ContactDAO interface.
 */
public class ContactDAOImpl implements ContactDAO {

  @Inject
  private DomainFactory myDomainFactory;
  @Inject
  private DALBackServices dalServices;
  @Inject
  private ViewCompanyDAO companyDAO;
  @Inject
  private ViewUEInscriptionDAO inscriptionDAO;

  /**
   * Retrieves a contact by its ID.
   *
   * @param contactId The ID of the contact to retrieve.
   * @return The contact DTO if found, null otherwise.
   * @throws FatalException if the contact is not found.
   */
  public ContactDTO getContactById(int contactId) {
    PreparedStatement preparedStatement = dalServices.getPreparedStatement(
        "SELECT * FROM pae.contacts c, pae.enterprises e, pae.inscriptions_UE i"
            + " WHERE c.enterprise = e.id_enterprise AND c.inscription_UE = i.id_inscription_UE"
            + " AND c.id_contact = ?");

    try {
      preparedStatement.setInt(1, contactId);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          return contactInfos(resultSet);
        }
      }
    } catch (SQLException e) {
      throw new FatalException("Contact not found", Status.BAD_REQUEST);
    }
    return null;
  }

  /**
   * Extracts contact information from a ResultSet.
   *
   * @param resultSet The ResultSet to extract information from.
   * @return A ContactDTO populated with the extracted information.
   * @throws FatalException if an SQL error occurs.
   */
  public ContactDTO contactInfos(ResultSet resultSet) {
    ContactDTO contactDTO = myDomainFactory.getContact();
    ViewCompanyDTO company;
    ViewUEInscriptionDTO ueInscription;

    try {
      contactDTO.setId(resultSet.getInt("id_contact"));
      contactDTO.setState(resultSet.getString("state"));
      contactDTO.setReasonForRefusal(resultSet.getString("reason_for_refusal"));
      contactDTO.setFollowed(resultSet.getBoolean("is_followed"));
      contactDTO.setMeetingPlace(resultSet.getString("meeting_place"));
      company = companyDAO.companyInfos(resultSet);
      contactDTO.setCompany((ViewCompany) company);
      ueInscription = inscriptionDAO.ueInscriptionInfos(resultSet);
      contactDTO.setInscriptionUE((ViewUEInscription) ueInscription);
    } catch (SQLException e) {
      throw new FatalException(e);
    }

    return contactDTO;
  }

  /**
   * Updates a contact in the database.
   *
   * @param contactDTO The contact DTO to update.
   * @throws FatalException if an SQL error occurs.
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
        ps.setInt(2, contactDTO.getCompany().getId());
        ps.setInt(3, contactDTO.getInscriptionUE().getId());
        ps.setString(4, contactDTO.getReasonForRefusal());
        ps.setBoolean(5, contactDTO.isFollowed());
        ps.setString(6, contactDTO.getMeetingPlace());
        ps.setInt(7, contactDTO.getId());

        ps.execute();
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }
  }

  /**
   * Method to retrieve contacts by their user's id.
   *
   * @param id The ID of the user whose contacts to retrieve.
   * @return A list of ContactDTO object representing the contacts, or null if not found.
   * @throws FatalException if not found in the database.
   */
  public ArrayList<ContactDTO> getContactsByUserId(int id) {
    PreparedStatement preparedStatement = dalServices.getPreparedStatement(
        "SELECT * FROM pae.contacts c, pae.enterprises e, pae.inscriptions_UE i, pae.users u"
            + " WHERE c.enterprise = e.id_enterprise AND c.inscription_UE = i.id_inscription_UE"
            + " AND i.student = u.id_user AND u.id_user = ?");

    return getCorrespondingContacts(preparedStatement, id);
  }

  /**
   * Method to retrieve taken contacts by their user's id.
   *
   * @param id The ID of the user whose taken contacts to retrieve.
   * @return A list of ContactDTO object representing the contacts, or null if not found.
   * @throws FatalException if not found in the database.
   */
  public ArrayList<ContactDTO> getTakenContactsByUserId(int id) {
    PreparedStatement preparedStatement = dalServices.getPreparedStatement(
        "SELECT * FROM pae.contacts c, pae.enterprises e, pae.inscriptions_UE i, pae.users u"
            + " WHERE c.enterprise = e.id_enterprise AND c.inscription_UE = i.id_inscription_UE"
            + " AND i.student = u.id_user AND c.state = 'pris' AND u.id_user = ?");

    return getCorrespondingContacts(preparedStatement, id);
  }

  /**
   * Method to get the wanted set of contacts.
   *
   * @param id The ID of the user whose contacts to retrieve.
   * @param ps The prepared statement containing the information about which contacts are wanted.
   * @return A list of ContactDTO object representing the contacts, or null if not found.
   * @throws FatalException if not found in the database.
   */
  private ArrayList<ContactDTO> getCorrespondingContacts(PreparedStatement ps, int id) {
    try {
      ps.setInt(1, id);
    } catch (SQLException e) {
      throw new FatalException(e);
    }

    ArrayList<ContactDTO> contacts = new ArrayList<>();
    ContactDTO contact;
    try (ResultSet resultSet = ps.executeQuery()) {
      while (resultSet.next()) {
        contact = contactInfos(resultSet);
        contacts.add(contact);
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
      System.exit(1);
    } finally {
      try {
        ps.close();
      } catch (SQLException e) {
        e.printStackTrace();
        throw new FatalException(e);
      }
    }
    return contacts;
  }


  /**
   * Inserts a new contact into the database.
   *
   * @param contactDTO The contact data to be inserted.
   * @return The contact data after insertion.
   * @throws FatalException if an unrecoverable error occurs during database operation.
   */
  @Override
  public ContactDTO insert(ContactDTO contactDTO) {
    try {
      String query = """
              INSERT INTO pae.contacts (
              state,
              enterprise,
              inscription_ue,
              reason_for_refusal,
              is_followed,
              meeting_place)
          VALUES ('initié',
          (SELECT e.id_enterprise
           FROM pae.enterprises e
           WHERE e.trade_name LIKE ?),
          (SELECT DISTINCT i.id_inscription_ue
           FROM pae.users u, pae.inscriptions_ue i
           WHERE u.id_user = i.student
           AND u.id_user =  ?),
          null, true, null)
          RETURNING *;
            """;

      System.out.println("Generated SQL query: " + query);

      // String tradeName = "N"; // Or any other search term
      // String wildcardTradeName = "%" + tradeName + "%";
      // changer le wildcard en id de l entreprise

      try (PreparedStatement ps = dalServices.getPreparedStatement(query)) {

        System.out.println("2 Generated SQL query : " + query);

        ps.setString(1, contactDTO.getTradeName());
        ps.setInt(2, contactDTO.getUserId());

        System.out.println("ContactDAOImpl -------> Enterprise : "
            + contactDTO.getTradeName());
        System.out.println("ContactDAOImpl -------> UserId : "
            + contactDTO.getUserId());

        System.out.println("ContactDAOImpl ----> ps : " + ps);
        ps.execute();
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException(e);
    }
    System.out.println("ContactDAOImpl --> contactDTO : " + contactDTO);
    return contactDTO;
  }
}
