package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.Contact;
import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.dal.ContactDAO;
import be.vinci.pae.dal.DALServices;
import jakarta.inject.Inject;
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
        throw new IllegalArgumentException("Contact not found");
      }

      Contact contactBiz = (Contact) contact;

      if (place == null) {
        throw new IllegalArgumentException("Place field cannot be null");
      }

      if (contactBiz.initieState(contact)) {
        contact.setState("pris");
        contact.setMeetingPlace(place);

        System.out.println("contact ucc : " + contact);

        contactDAO.update(contact);
        return contact;
      } else {
        throw new IllegalArgumentException("Invalid contact state");
      }
    } catch (Exception e) {
      dalServices.rollBack();
      throw e;
    } finally {
      dalServices.commit();
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
      return contactDAO.getContactById(idContact);
    } catch (Exception e) {
      dalServices.rollBack();
      throw e;
    } finally {
      dalServices.commit();
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
        throw new IllegalArgumentException("Contact not found");
      }

      Contact contactBiz = (Contact) contact;

      if (!contactBiz.isFollowed(contact)) {
        throw new IllegalArgumentException("Invalid contact state");
      } else {
        contact.setFollowed(false);
        contact.setState("abandonné");

        System.out.println("contact ucc stop following : " + contact);

        contactDAO.update(contact);
        return contact;
      }
    } catch (Exception e) {
      dalServices.rollBack();
      throw e;
    } finally {
      dalServices.commit();
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
        throw new IllegalArgumentException("Contact not found");
      }

      if (reason == null) {
        throw new IllegalArgumentException("Reason field cannot be null");
      }

      Contact contactBiz = (Contact) contact;

      if (contactBiz.prisState(contact)) {
        contact.setState("refusé");
        contact.setReasonForRefusal(reason);

        System.out.println("contact ucc company refused internship : " + contact);

        contactDAO.update(contact);
        return contact;
      } else {
        throw new IllegalArgumentException("Invalid contact state");
      }
    } catch (Exception e) {
      dalServices.rollBack();
      throw e;
    } finally {
      dalServices.commit();
    }
  }

  /**
   * Returns the taken contacts corresponding to the user corresponding to the id.
   *
   * @param id the user's id
   * @return the taken contacts corresponding to the user
   */
  public ArrayList<ContactDTO> getTakenContactsByUserId(int id) {
    dalServices.start();
    try {
      ArrayList<ContactDTO> contactDTOS = contactDAO.getTakenContactsByUserId(id);
      return contactDTOS;
    } catch (Exception e) {
      System.out.println("ROLLBACK");
      dalServices.rollBack();
      throw e;
    } finally {
      System.out.println("ContactUCCImpl -----> COMMITT");
      dalServices.commit();
    }
  }

  /**
   * Returns all the contacts corresponding to the user corresponding to the id.
   *
   * @param id the user's id
   * @return all the contacts corresponding to the user
   */
  public ArrayList<ContactDTO> getContactsByUserId(int id) {
    dalServices.start();
    try {
      ArrayList<ContactDTO> contactDTOS = contactDAO.getContactsByUserId(id);
      return contactDTOS;
    } catch (Exception e) {
      System.out.println("ROLLBACK");
      dalServices.rollBack();
      throw e;
    } finally {
      System.out.println("ContactUCCImpl -----> COMMITT");
      dalServices.commit();
    }
  }


  /**
   * Adds a new contact.
   *
   * @param contactDTO The contact data to be added.
   * @return The added contact data.
   */
  @Override
  public ContactDTO addContact(ContactDTO contactDTO) {
    System.out.println("ContactUCCImpl ------> contactDTO : " + contactDTO);
    dalServices.start();
    try {
      System.out.println("ContactUCCImpl ------> contactDTO : " + contactDTO);
      return contactDAO.insert(contactDTO);

    } catch (Exception e) {
      dalServices.rollBack();
      System.out.println("ContactUCCImpl ----> e.getMessage() = " + e.getMessage());
      throw e;
    } finally {
      dalServices.commit();
    }
  }
}
