package be.vinci.pae.business.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Represents an internship supervisor data transfer object (DTO) with various properties.
 * This interface defines methods for accessing and modifying internship supervisor-related information.
 */
@JsonDeserialize(as = UserImpl.class)
public interface InternshipSupervisorDTO {

  /**
   * Returns the supervisor's id.
   *
   * @return the supervisor's id
   */
  int getId();

  /**
   * Sets the supervisor's id.
   *
   * @param id the supervisor's id to set
   */
  void setId(int id);

  /**
   * Returns the supervisor's first name
   *
   * @return the supervisor's first name
   */
  String getFirstName();

  /**
   * Sets the supervisor's first name.
   *
   * @param firstName the supervisor's first name to set
   */
  void setFirstName(String firstName);

  /**
   * Returns the supervisor's last name.
   *
   * @return the supervisor's last name
   */
  String getLastName();

  /**
   * Sets the supervisor's last name.
   *
   * @param lastName the supervisor's last name to set
   */
  void setLastName(String lastName);

  /**
   * Sets the supervisor's phone number.
   *
   * @param phoneNumber the supervisor's phone number to set
   */
  void setPhoneNumber(String phoneNumber);

  /**
   * Returns the supervisor's email.
   *
   * @return the supervisor's email
   */
  String getEmail();

  /**
   * Sets the supervisor's email.
   *
   * @param email the supervisor's email to set
   */
  void setEmail(String email);

  /**
   * Sets the supervisor's company.
   *
   * @param company the supervisor's company to set
   */
  void setCompany(ViewCompany company);
}
