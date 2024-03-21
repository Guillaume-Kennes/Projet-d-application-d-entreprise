package be.vinci.pae.dal;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.InternshipSupervisorDTO;
import be.vinci.pae.business.domain.ViewCompany;
import be.vinci.pae.business.domain.ViewCompanyDTO;
import be.vinci.pae.business.domain.ViewContactDTO;
import be.vinci.pae.business.domain.ViewUEInscription;
import be.vinci.pae.business.domain.ViewUEInscriptionDTO;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementation of the ViewContactDAO interface.
 * Provides methods for retrieving contact-related data from the database.
 */
public class ViewContactDAOImpl implements ViewContactDAO{

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
    ViewUEInscriptionDTO UEInscription;

    try {
      contact.setId(resultSet.getInt("id_contact"));
      contact.setState(resultSet.getString("state"));
      contact.setMeetingPlace(resultSet.getString("meeting_place"));
      contact.setReasonRefusal(resultSet.getString("reason_for_refusal"));
      contact.setFollowed(resultSet.getBoolean("is_followed"));
      company = companyDAO.companyInfos(resultSet);
      contact.setCompany((ViewCompany) company);
      UEInscription = inscriptionDAO.ueInscriptionInfos(resultSet);
      contact.setUeInscription((ViewUEInscription) UEInscription);
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
   * @return A ViewContactDTO object representing the contact with the specified ID, or null if not found.
   *
   * @throws IllegalArgumentException if the contact is not found in the database.
   */
  public ViewContactDTO getContactById(int id) {
    PreparedStatement preparedStatement = dalServices.getPreparedStatement(
        "SELECT * FROM pae.contacts c, pae.entreprises e, pae.inscriptions_UE i"
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
}
