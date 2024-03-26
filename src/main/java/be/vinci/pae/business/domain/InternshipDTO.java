package be.vinci.pae.business.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Represents an internship data transfer object (DTO) with various properties.
 * This interface defines methods for accessing and modifying internship-related information.
 */
@JsonDeserialize(as = UserImpl.class)
public interface InternshipDTO {

  /**
   * Returns the internship's id.
   *
   * @return the internship's id
   */
  int getId();

  /**
   * Sets the internship's id.
   *
   * @param id the internship's id to set
   */
  void setId(int id);

  /**
   * Returns the internship's contact.
   *
   * @return the internship's contact
   */
  Contact getContact();

  /**
   * Sets the internship's contact.
   *
   * @param contact the internship's contact to set
   */
  void setContact(Contact contact);

  /**
   * Returns the internship's supervisor.
   *
   * @return the internship's supervisor
   */
  InternshipSupervisor getSupervisor();

  /**
   * Sets the internship's supervisor.
   *
   * @param supervisor the internship's supervisor to set
   */
  void setSupervisor(InternshipSupervisor supervisor);

  /**
   * Returns the internship's project.
   *
   * @return the internship's project
   */
  String getProject();

  /**
   * Sets the internship's project.
   *
   * @param project the internship's project to set
   */
  void setProject(String project);

  /**
   * Returns the internship's signature date.
   *
   * @return the internship's signature date
   */
  String getDate();

  /**
   * Sets the internship's signature date.
   *
   * @param date the internship's signature date to set
   */
  void setDate(String date);
}
