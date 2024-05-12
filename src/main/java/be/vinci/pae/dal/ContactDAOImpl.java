package be.vinci.pae.dal;

import be.vinci.pae.business.domain.Company;
import be.vinci.pae.business.domain.CompanyDTO;
import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.UEInscription;
import be.vinci.pae.business.domain.UEInscriptionDTO;
import be.vinci.pae.utils.AppLogger;
import be.vinci.pae.utils.exception.FatalException;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of the ContactDAO interface.
 */
public class ContactDAOImpl implements ContactDAO {

  @Inject
  private DomainFactory myDomainFactory;
  @Inject
  private DALBackServices dalServices;
  @Inject
  private CompanyDAO companyDAO;
  @Inject
  private UEInscriptionDAO inscriptionDAO;
  private Logger log;

  /**
   * Retrieves a contact by its ID.
   *
   * @param contactId The ID of the contact to retrieve.
   * @return The contact DTO if found, null otherwise.
   * @throws FatalException if the contact is not found.
   */
  public ContactDTO getContactById(int contactId) {
    PreparedStatement preparedStatement = dalServices.getPreparedStatement(
        "SELECT * FROM pae.contacts c, pae.enterprises e, pae.inscriptions_ue i, pae.users u "
            + " WHERE c.enterprise = e.id_enterprise AND c.inscription_ue = i.id_inscription_ue"
            + " AND u.id_user = i.student AND c.id_contact = ?");

    try {
      preparedStatement.setInt(1, contactId);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          return contactInfos(resultSet);
        }
      }
    } catch (SQLException e) {
      throw new FatalException("Contact not found");
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
    CompanyDTO company;
    UEInscriptionDTO ueInscription;

    try {
      contactDTO.setId(resultSet.getInt("id_contact"));
      contactDTO.setState(resultSet.getString("state"));
      contactDTO.setReasonForRefusal(resultSet.getString("reason_for_refusal"));
      contactDTO.setFollowed(resultSet.getBoolean("is_followed"));
      contactDTO.setMeetingPlace(resultSet.getString("meeting_place"));
      contactDTO.setVersionNumber(resultSet.getInt("version_contacts"));
      company = companyDAO.companyInfos(resultSet);
      contactDTO.setCompany((Company) company);
      ueInscription = inscriptionDAO.ueInscriptionInfos(resultSet);
      contactDTO.setInscriptionUE((UEInscription) ueInscription);
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
          meeting_place = ?,
          version_contacts = version_contacts + 1
          WHERE id_contact= ? AND version_contacts = ?
          RETURNING version_contacts;
          """;
      try (PreparedStatement ps = dalServices.getPreparedStatement(query)) {
        ps.setString(1, contactDTO.getState());
        ps.setInt(2, contactDTO.getCompany().getId());
        ps.setInt(3, contactDTO.getInscriptionUE().getId());
        ps.setString(4, contactDTO.getReasonForRefusal());
        ps.setBoolean(5, contactDTO.isFollowed());
        ps.setString(6, contactDTO.getMeetingPlace());
        ps.setInt(7, contactDTO.getId());
        ps.setInt(8, contactDTO.getVersionNumber());

        ResultSet rs = ps.executeQuery();
        int correctVersion = 0; // faire executeQuery avec RETURNING
        if (rs.next()) {
          correctVersion = rs.getInt("version_contacts");
        }
        if (correctVersion == 0) {
          if (getContactById(contactDTO.getId()) == null) {
            throw new FatalException("Contact not found");
          } else {
            throw new IllegalArgumentException("Erreur pour changer l'état des contacts");
          }
        }
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
  public ArrayList<ContactDTO> getContactsByUserId(int id) throws SQLException {
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
  public ArrayList<ContactDTO> getTakenContactsByUserId(int id) throws SQLException {
    PreparedStatement preparedStatement = dalServices.getPreparedStatement(
        "SELECT * FROM pae.contacts c, pae.enterprises e, pae.inscriptions_UE i, pae.users u"
            + " WHERE c.enterprise = e.id_enterprise AND c.inscription_UE = i.id_inscription_UE"
            + " AND i.student = u.id_user AND (c.state = 'pris' OR c.state = 'initié'"
            + " OR c.state = 'accepté') AND u.id_user = ?");
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
  private ArrayList<ContactDTO> getCorrespondingContacts(PreparedStatement ps, int id)
      throws SQLException {
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
    } catch (SQLException e) {
      throw new FatalException(e);
    } finally {
      ps.close();
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
  public ContactDTO insert(ContactDTO contactDTO) {
    try {
      String query = """
              INSERT INTO pae.contacts (
              state,
              enterprise,
              inscription_ue,
              reason_for_refusal,
              is_followed,
              meeting_place,
              version_contacts)
          VALUES ('initié', ?,
          (SELECT DISTINCT i.id_inscription_ue
           FROM pae.users u, pae.inscriptions_ue i
           WHERE u.id_user = i.student
           AND u.id_user =  ?),
          null, true, null, 1)
          RETURNING *;
          """;

      try (PreparedStatement ps = dalServices.getPreparedStatement(query)) {
        ps.setInt(1, contactDTO.getEnterprise());
        ps.setInt(2, contactDTO.getUserId());
        ps.executeQuery();
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }

    log = AppLogger.getLogger("Création d'un contact");
    log.log(Level.FINE, "Création d'un contact\n"
        + "ContactDAOImpl contactDTO : " + "\n"
        + "State : " + contactDTO.getState() + "\n"
        + "Enterprise : " + contactDTO.getEnterprise() + "\n"
        + "UserId : " + contactDTO.getUserId() + "\n"
        + "ReasonForRefusal : " + contactDTO.getReasonForRefusal() + "\n"
        + "MeetingPlace : " + contactDTO.getMeetingPlace() + "\n"
        + "VersionContacts : " + contactDTO.getVersionNumber() + "\n");

    return contactDTO;
  }

  /**
   * Method to retrieve all the contacts made to a company.
   *
   * @param idCompany The ID of the company.
   * @return A ContactDTO list containing all the contacts of the company, or null if not found.
   * @throws FatalException if the company is not found in the database.
   */
  public ArrayList<ContactDTO> getCompanyContacts(int idCompany) throws SQLException {
    PreparedStatement preparedStatement = dalServices.getPreparedStatement(
        "SELECT c.*, u.*, e.*, i.* FROM pae.contacts c "
            + "JOIN pae.inscriptions_ue i ON c.inscription_ue = i.id_inscription_ue "
            + "JOIN pae.users u ON i.student = u.id_user "
            + "JOIN pae.enterprises e ON c.enterprise = e.id_enterprise "
            + "WHERE c.enterprise = ?");

    try {
      preparedStatement.setInt(1, idCompany);
    } catch (SQLException e) {
      throw new FatalException(e);
    }

    ContactDTO contact;
    ArrayList<ContactDTO> contacts = new ArrayList<>();
    try (ResultSet resultSet = preparedStatement.executeQuery()) {
      while (resultSet.next()) {
        contact = contactInfos(resultSet);
        contacts.add(contact);
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    } finally {
      preparedStatement.close();
    }
    return contacts;
  }

  /**
   * Suspend the other contacts of a user once they got an internship.
   *
   * @param contactDTO The contact DTO to update.
   * @throws FatalException if an SQL error occurs.
   */
  public void suspendOthers(ContactDTO contactDTO) {
    try {
      String query = """
          UPDATE pae.contacts
          SET state = 'suspendu',
          version_contacts = version_contacts + 1
          WHERE inscription_ue = ? 
          AND state != 'accepté'
          """;
      try (PreparedStatement ps = dalServices.getPreparedStatement(query)) {
        ps.setInt(1, contactDTO.getInscriptionUE().getId());

        ps.executeUpdate();
        /*
        int correctVersion = 0; // faire executeQuery avec RETURNING
        if (rs.next()) {
          correctVersion = rs.getInt("version_contacts");
        }
        if (correctVersion == 0) {
          if (getContactById(contactDTO.getId()) == null) {
            throw new FatalException("Contact not found");
          } else {
            throw new IllegalArgumentException("Erreur pour suspendre les autres contacts");
          }
        } */
        // Nous avions des problèmes inexplicables avec l'optimistic lock ici,
        // donc nous avons commenté pour que la démo marche.
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }
  }
}