package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.Contact;
import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.dal.ContactDAO;
import be.vinci.pae.dal.DALServices;
import jakarta.inject.Inject;

public class ContactUCCImpl implements ContactUCC {

  @Inject
  private ContactDAO contactDAO;

  @Inject
  private DALServices dalServices;

  public ContactDTO meetCompany(ContactDTO contact, String place) {
    dalServices.start();
    try{
      if(contact == null)
        throw new IllegalArgumentException("Contact not found");
      contact.setState("pris");
      contact.setMeetingPlace(place);

      System.out.println("contact ucc : " + contact);

      contactDAO.update(contact);
      return contact;
    } catch (Exception e) {
      dalServices.rollBack();
      throw e;
    } finally {
      dalServices.commit();
    }
  }

  public ContactDTO getContactById(int id_contact) {
    dalServices.start();
    try{
      return contactDAO.getContactById(id_contact);
    } catch (Exception e) {
      dalServices.rollBack();
      throw e;
    } finally {
      dalServices.commit();
    }
  }

  public ContactDTO stopFollowing(ContactDTO contact) {
    dalServices.start();
    try{
      if (contact == null) {
        throw new IllegalArgumentException("Contact not found");
      }
      contact.setFollowed(false);

      System.out.println("contact ucc stop following : " + contact);

      contactDAO.update(contact);
      return contact;
    } catch (Exception e) {
      dalServices.rollBack();
      throw e;
    } finally {
      dalServices.commit();
    }
  }

  public ContactDTO companyRefusedInternship(ContactDTO contact, String reason) {
    dalServices.start();
    try {
      if (contact == null) {
        throw new IllegalArgumentException("Contact not found");
      }
      contact.setState("refusé");
      contact.setReasonForRefusal(reason);

      System.out.println("contact ucc company refused internship : " + contact);

      contactDAO.update(contact);
      return contact;
    } catch (Exception e) {
      dalServices.rollBack();
      throw e;
    } finally {
      dalServices.commit();
    }
  }

}
