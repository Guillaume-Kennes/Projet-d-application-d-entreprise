package be.vinci.pae.business.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Implementation class for the ViewUEInscription interface.
 * This class provides implementations for various UEInscription-related methods.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ViewUEInscriptionImpl implements ViewUEInscription{

  private int id;
  private User student;
  private String schoolYear;

  /**
   * Default constructor for ViewUEInscriptionImpl.
   */
  public ViewUEInscriptionImpl() {
  }

  /**
   * Get the id of the UEInscription.
   *
   * @return The id of the UEInscription
   */
  public int getId() {
    return this.id;
  }

  /**
   * Set the id of the UEInscription.
   *
   * @param id The id to set.
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Set the student of the UEInscription.
   *
   * @param student The id to set.
   */
  public void setStudent(User student) {
    this.student = student;
  }

  /**
   * Set the school year of the UEInscription.
   *
   * @param schoolYear The school year to set.
   */
  public void setSchoolYear(String schoolYear) {
    this.schoolYear = schoolYear;
  }
}
