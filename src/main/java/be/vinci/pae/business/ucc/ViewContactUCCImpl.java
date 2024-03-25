package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.ViewContactDTO;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.ViewContactDAO;
import jakarta.inject.Inject;
import java.util.ArrayList;

/**
 * Implementation of the ViewContactUCC interface.
 * Provides methods related to contact operations.
 */
public class ViewContactUCCImpl implements ViewContactUCC {

  @Inject
  private ViewContactDAO contactDAO;
  @Inject
  private DALServices dalServices;

  /**
   * Returns the taken contacts corresponding to the user corresponding to the id.
   *
   * @param id the user's id
   *
   * @return the taken contacts corresponding to the user
   */
  public ArrayList<ViewContactDTO> getTakenContactsByUserId(int id) {
    dalServices.start();
    try {
      ArrayList<ViewContactDTO> contactDTOS = contactDAO.getTakenContactsByUserId(id);
      return contactDTOS;
    } catch (Exception e) {
      System.out.println("ROLLBACK");
      dalServices.rollBack();
      throw e;
    } finally {
      System.out.println("COMMITT");
      dalServices.commit();
    }
  }

  /**
   * Returns all the contacts corresponding to the user corresponding to the id.
   *
   * @param id the user's id
   *
   * @return all the contacts corresponding to the user
   */
  public ArrayList<ViewContactDTO> getContactsByUserId(int id) {
    dalServices.start();
    try {
      ArrayList<ViewContactDTO> contactDTOS = contactDAO.getContactsByUserId(id);
      return contactDTOS;
    } catch (Exception e) {
      System.out.println("ROLLBACK");
      dalServices.rollBack();
      throw e;
    } finally {
      System.out.println("COMMITT");
      dalServices.commit();
    }
  }
}
