package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.InternshipDTO;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.InternshipDAO;
import jakarta.inject.Inject;

/**
 * Implementation of the InternshipUCC interface.
 * Provides methods related to internship operations.
 */
public class InternshipUCCImpl implements InternshipUCC{

  @Inject
  private InternshipDAO internshipDAO;
  @Inject
  private DALServices dalServices;

  /**
   * Returns the internship corresponding to the user corresponding to the id.
   *
   * @param id the user's id
   *
   * @return the internship corresponding to the user
   */
  public InternshipDTO getInternshipByUserId(int id) {
    dalServices.start();
    try{
      InternshipDTO internshipDTO = internshipDAO.getInternshipByUserId(id);
      return internshipDTO;
    }catch(Exception e){
      System.out.println("ROLLBACK");
      dalServices.rollBack();
      throw e;
    } finally {
      System.out.println("COMMITT");
      dalServices.commit();
    }
  }
}
