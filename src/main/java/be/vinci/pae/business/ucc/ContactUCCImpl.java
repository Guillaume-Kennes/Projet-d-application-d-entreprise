package be.vinci.pae.business.ucc;


import be.vinci.pae.business.domain.ContactDTO;
import be.vinci.pae.dal.ContactDAO;
import be.vinci.pae.dal.DALServices;
import jakarta.inject.Inject;

public class ContactUCCImpl implements ContactUCC {

  @Inject
  private ContactDAO contactDAO;
  @Inject
  private DALServices dalServices;

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
