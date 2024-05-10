package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.Contact;
import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.dal.ContactDAO;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.utils.exception.BusinessException;
import be.vinci.pae.utils.exception.NotFoundException;
import jakarta.inject.Inject;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Implementation of the ContactUCC interface.
 */
public class ContactUCCImpl implements ContactUCC {

  @Inject
  private ContactDAO contactDAO;

  @Inject
  private DALServices dalServices;

  /**
   * Arranges a meeting with a company.
   *
   * @param contact The contact for the meeting.
   * @param place   The place of the meeting.
   * @return The updated contact after the meeting.
   */
  public ContactDTO meetCompany(ContactDTO contact, String place) {
    dalServices.start();
    try {
      if (contact == null) {
        dalServices.rollBack();
        throw new NotFoundException("Contact not found");
      }

      Contact contactBiz = (Contact) contact;

      if (place == null) {
        dalServices.rollBack();
        throw new BusinessException("Place field cannot be null");
      }

      if (contactBiz.initieState(contact)) {
        contact.setState("pris");
        contact.setMeetingPlace(place);

        contactDAO.update(contact);
        dalServices.commit();

        return contact;
      } else {
        dalServices.rollBack();
        throw new BusinessException("Invalid contact state");
      }
    } catch (Exception e) {
      dalServices.rollBack();
      throw e;
    }
  }

  /**
   * Retrieves a contact by its ID.
   *
   * @param idContact The ID of the contact.
   * @return The contact with the given ID.
   */
  public ContactDTO getContactById(int idContact) {
    dalServices.start();
    try {
      ContactDTO contact = contactDAO.getContactById(idContact);
      dalServices.commit();
      return contact;
    } catch (Exception e) {
      dalServices.rollBack();
      throw e;
    }
  }

  /**
   * Stops following a contact.
   *
   * @param contact The contact to stop following.
   * @return The updated contact after stopping the follow.
   */
  public ContactDTO stopFollowing(ContactDTO contact) {
    System.out.println("Contact " + contact);
    dalServices.start();
    try {
      if (contact == null) {
        dalServices.rollBack();
        throw new NotFoundException("Contact not found");
      }

      Contact contactBiz = (Contact) contact;

      if (!contactBiz.isFollowed(contact)) {
        dalServices.rollBack();
        throw new BusinessException("Invalid contact state");

      } else {
        contact.setFollowed(false);
        contact.setState("abandonné");

        contactDAO.update(contact);
        dalServices.commit();
        return contact;
      }
    } catch (Exception e) {
      dalServices.rollBack();
      throw e;
    }
  }

  /**
   * Handles the case when a company refuses an internship.
   *
   * @param contact The contact for the company.
   * @param reason  The reason for refusal.
   * @return The updated contact after the refusal.
   */
  public ContactDTO companyRefusedInternship(ContactDTO contact, String reason) {
    dalServices.start();
    try {
      if (contact == null) {
        dalServices.rollBack();
        throw new NotFoundException("Contact not found");
      }

      if (reason == null) {
        dalServices.rollBack();
        throw new BusinessException("Reason field cannot be null");
      }

      Contact contactBiz = (Contact) contact;

      if (contactBiz.prisState(contact)) {
        contact.setState("refusé");
        contact.setReasonForRefusal(reason);

        contactDAO.update(contact);
        dalServices.commit();
        return contact;
      } else {
        dalServices.rollBack();
        throw new BusinessException("Invalid contact state");
      }
    } catch (Exception e) {
      dalServices.rollBack();
      throw e;
    }
  }

  /**
   * Returns the taken contacts corresponding to the user corresponding to the id.
   *
   * @param id the user's id
   * @return the taken contacts corresponding to the user
   */
  public ArrayList<ContactDTO> getTakenContactsByUserId(int id) throws SQLException {
    dalServices.start();
    try {
      ArrayList<ContactDTO> contactDTOS = contactDAO.getTakenContactsByUserId(id);

      System.out.println("ContactUCCImpl -----> COMMITT");
      dalServices.commit();
      return contactDTOS;
    } catch (Exception e) {
      dalServices.rollBack();
      throw e;
    }
  }

  /**
   * Returns all the contacts corresponding to the user corresponding to the id.
   *
   * @param id the user's id
   * @return all the contacts corresponding to the user
   */
  public ArrayList<ContactDTO> getContactsByUserId(int id) throws SQLException {
    try {
      dalServices.start();
      ArrayList<ContactDTO> contactDTOS = contactDAO.getContactsByUserId(id);
      System.out.println("ContactUCCImpl -----> COMMITT");
      dalServices.commit();
      return contactDTOS;
    } catch (Exception e) {
      dalServices.rollBack();
      throw e;
    }
  }

  /**
   * Adds a new contact.
   *
   * @param contactDTO The contact data to be added.
   * @return The added contact data.
   */
  public ContactDTO addContact(ContactDTO contactDTO) {
    dalServices.start();
    try {
      System.out.println("ContactUCCImpl ------> contactDTO : " + contactDTO);
      System.out.println("ContactUCCImpl ------> userId : " + contactDTO.getUserId());
      System.out.println("ContactUCCImpl ------> enterprise : " + contactDTO.getEnterprise());
      ContactDTO contact = contactDAO.insert(contactDTO);
      dalServices.commit();
      return contact;
    } catch (Exception e) {
      dalServices.rollBack();
      throw e;
    }
  }

  /**
   * Retrieves a list of all contacts made by a company.
   *
   * @param id The id of the company.
   * @return A list of ContactDTO objects.
   */
  public ArrayList<ContactDTO> getAllContacts(int id) throws SQLException {
    dalServices.start();
    try {
      ArrayList<ContactDTO> contactList = contactDAO.getCompanyContacts(id);

      dalServices.commit();
      return contactList;

    } catch (Exception e) {
      dalServices.rollBack();
      throw e;
    }
  }

  /**
   * Accepts a contact as an internship.
   *
   * @param contact The contact for the company.
   * @return The updated contact after the refusal.
   */
  public ContactDTO acceptInternship(ContactDTO contact) {
    dalServices.start();
    try {
      if (contact == null) {
        dalServices.rollBack();
        throw new NotFoundException("Contact not found");
      }

      Contact contactBiz = (Contact) contact;

      if (contactBiz.prisState(contact)) {
        contact.setState("accepté");

        contactDAO.update(contact);
        contactDAO.suspendOthers(contact);
        dalServices.commit();
        return contact;
      } else {
        dalServices.rollBack();
        throw new BusinessException("Invalid contact state");
      }
    } catch (Exception e) {
      dalServices.rollBack();
      throw e;
    }
  }
}
