package be.vinci.pae.business.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Represents an internship supervisor data transfer object (DTO) with various properties.
 * This interface defines methods for accessing and modifying supervisor-related information.
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
   * Returns the supervisor's first name.
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
   * Returns the supervisor's phone number.
   *
   * @return the supervisor's phone number
   */
  String getPhoneNumber();

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
   * Returns the supervisor's company.
   *
   * @return the supervisor's company
   */
  int getCompany();

  /**
   * Sets the supervisor's company.
   *
   * @param companyId the supervisor's company to set
   */
  void setCompany(int companyId);

  /**
   * Returns the version number of the supervisor.
   *
   * @return the version number of the supervisor
   */
  int getVersionNumber();

  /**
   * Sets the version number of the supervisor.
   *
   * @param versionNumber the version number to set
   */
  void setVersionNumber(int versionNumber);

  /**
   * Returns the trade name of the supervisor's company.
   *
   * @return the trade name of the supervisor's company
   */
  public String getTradeName();

  /**
   * Sets the trade name of the supervisor's company.
   *
   * @param tradeName the trade name to set
   */
  public void setTradeName(String tradeName);

  /**
   * Returns the designation of the supervisor.
   *
   * @return the designation of the supervisor
   */
  public String getDesignation();

  /**
   * Sets the designation of the supervisor.
   *
   * @param designation the designation to set
   */
  public void setDesignation(String designation);
}
