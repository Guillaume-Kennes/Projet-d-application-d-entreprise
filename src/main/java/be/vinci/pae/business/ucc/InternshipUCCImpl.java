package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.Internship;
import be.vinci.pae.business.domain.InternshipDTO;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.InternshipDAO;
import be.vinci.pae.utils.exception.NotFoundException;
import jakarta.inject.Inject;
import java.sql.Date;
import java.sql.SQLException;

/**
 * Implementation of the InternshipUCC interface. Provides methods related to internship
 * operations.
 */
public class InternshipUCCImpl implements InternshipUCC {

  @Inject
  private InternshipDAO internshipDAO;
  @Inject
  private DALServices dalServices;

  /**
   * Returns the internship corresponding to the user corresponding to the id.
   *
   * @param id the user's id
   * @return the internship corresponding to the user
   */
  public InternshipDTO getInternshipByUserId(int id) throws SQLException {
    dalServices.start();
    try {
      InternshipDTO internshipDTO = internshipDAO.getInternshipByUserId(id);
      System.out.println("Ucc Internship : " + internshipDTO);
      dalServices.commit();
      return internshipDTO;
    } catch (Exception e) {
      dalServices.rollBack();
      throw e;
    }
  }

  public InternshipDTO createAnInternship(int contact, int supervisor, String projet,
      Date signatureDate) {
    dalServices.start();
    try {
      InternshipDTO internshipDTO = internshipDAO.createAnInternship(contact, supervisor, projet,
          signatureDate);
      dalServices.commit();
      return internshipDTO;
    } catch (Exception e) {
      dalServices.rollBack();
      e.printStackTrace();
      throw e;
    }
  }

  @Override
  public InternshipDTO createOrModifyAnInternship(InternshipDTO internship, String subject) {
    dalServices.start();
    try {
      if (internship == null) {
        dalServices.rollBack();
        throw new NotFoundException("Internship is null");
      }

      if (subject == null) {
        dalServices.rollBack();
        throw new NotFoundException("Subject is null");
      }

      internship.setProject(subject);

      internshipDAO.update(internship);
      dalServices.commit();
      return internship;
    } catch (Exception e) {
      dalServices.rollBack();
      e.printStackTrace();
      throw e;
    }
  }

  @Override
  public InternshipDTO getInternshipById(int internshipId) {
    dalServices.start();
    try {
      InternshipDTO internship = internshipDAO.getInternshipById(internshipId);
      dalServices.commit();
      return internship;
    } catch (Exception e) {
      dalServices.rollBack();
      throw e;
    }
  }
}
