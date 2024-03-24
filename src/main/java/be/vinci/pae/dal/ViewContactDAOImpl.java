package be.vinci.pae.dal;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.ViewCompany;
import be.vinci.pae.business.domain.ViewCompanyDTO;
import be.vinci.pae.business.domain.ViewContactDTO;
import be.vinci.pae.business.domain.ViewUEInscription;
import be.vinci.pae.business.domain.ViewUEInscriptionDTO;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Implementation of the ViewContactDAO interface.
 * Provides methods for retrieving contact-related data from the database.
 */
public class ViewContactDAOImpl implements ViewContactDAO {

  @Inject
  private DomainFactory myDomainFactory;
  @Inject
  private DALBackServices dalServices;
  @Inject
  private ViewCompanyDAO companyDAO;
  @Inject
  private ViewUEInscriptionDAO inscriptionDAO;

  /**
   * Method to retrieve contact information from a ResultSet and map it to a ViewContactDTO object.
   *
   * @param resultSet The ResultSet containing contact information.
   *
   * @return A ViewContactDTO object populated with contact information from the ResultSet.
   */
  public ViewContactDTO contactInfos(ResultSet resultSet) {
    ViewContactDTO contact = myDomainFactory.getContact();
    ViewCompanyDTO company;
    ViewUEInscriptionDTO ueInscription;

    try {
      contact.setId(resultSet.getInt("id_contact"));
      contact.setState(resultSet.getString("state"));
      contact.setMeetingPlace(resultSet.getString("meeting_place"));
      contact.setReasonRefusal(resultSet.getString("reason_for_refusal"));
      contact.setFollowed(resultSet.getBoolean("is_followed"));
      company = companyDAO.companyInfos(resultSet);
      contact.setCompany((ViewCompany) company);
      ueInscription = inscriptionDAO.ueInscriptionInfos(resultSet);
      contact.setUeInscription((ViewUEInscription) ueInscription);
    } catch (SQLException e) {
      e.getMessage();
    }
    return contact;
  }

  /**
   * Method to retrieve a contact by its ID.
   *
   * @param id The ID of the contact to retrieve.
   *
   * @return A ViewContactDTO object representing the contact, or null if not found.
   *
   * @throws IllegalArgumentException if the contact is not found in the database.
   */
  public ViewContactDTO getContactById(int id) {
    PreparedStatement preparedStatement = dalServices.getPreparedStatement(
        "SELECT * FROM pae.contacts c, pae.enterprises e, pae.inscriptions_UE i"
            + " WHERE c.enterprise = e.id_enterprise AND c.inscription_UE = i.id_inscription_UE"
            + " AND c.id_contact = ?");
    try {
      preparedStatement.setInt(1, id);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    ViewContactDTO contact = myDomainFactory.getContact();
    try (ResultSet resultSet = preparedStatement.executeQuery()) {
      if (resultSet.next()) {
        contact = contactInfos(resultSet);
      } else {
        contact = null;
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
    return contact;
  }

  /**
   * Method to retrieve contacts by their user's id.
   *
   * @param id The ID of the user whose contacts to retrieve.
   *
   * @return A list of ViewContactDTO object representing the contacts, or null if not found.
   *
   * @throws IllegalArgumentException if not found in the database.
   */
  public ArrayList<ViewContactDTO> getContactsByUserId(int id) {
    PreparedStatement preparedStatement = dalServices.getPreparedStatement(
        "SELECT * FROM pae.contacts c, pae.enterprises e, pae.inscriptions_UE i, pae.users u"
            + " WHERE c.enterprise = e.id_enterprise AND c.inscription_UE = i.id_inscription_UE"
            + " AND i.student = u.id_user AND u.id_user = ?");

    return getCorrespondingContacts(preparedStatement, id);
  }

  @Override
  public ArrayList<ViewContactDTO> getTakenContactsByUserId(int id) {
    PreparedStatement preparedStatement = dalServices.getPreparedStatement(
        "SELECT * FROM pae.contacts c, pae.enterprises e, pae.inscriptions_UE i, pae.users u"
            + " WHERE c.enterprise = e.id_enterprise AND c.inscription_UE = i.id_inscription_UE"
            + " AND i.student = u.id_user AND c.state = 'pris' AND u.id_user = ?");

    return getCorrespondingContacts(preparedStatement, id);
  }

  private ArrayList<ViewContactDTO> getCorrespondingContacts(PreparedStatement ps, int id) {
    try {
      ps.setInt(1, id);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    ArrayList<ViewContactDTO> contacts = new ArrayList<>();
    ViewContactDTO contact;
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
      }
    }
    return contacts;
  }
}
