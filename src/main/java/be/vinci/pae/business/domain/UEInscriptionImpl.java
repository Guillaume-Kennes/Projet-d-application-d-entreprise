package be.vinci.pae.business.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Implementation class for the ViewUEInscription interface. This class provides implementations for
 * various UEInscription-related methods.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UEInscriptionImpl implements UEInscription {

  private int id;
  private User student;
  private String schoolYear;

  /**
   * Default constructor for ViewUEInscriptionImpl.
   */
  public UEInscriptionImpl() {
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
   * Get the user of the UEInscription.
   *
   * @return The user of the UEInscription
   */
  public User getStudent() {
    System.out.println("ViewDEMES2 ---> student : " + student);
    return student;
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
   * Get the school year of the UEInscription.
   *
   * @return The school year of the UEInscription
   */
  public String getSchoolYear() {
    return schoolYear;
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
