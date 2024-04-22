package be.vinci.pae.business.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.sql.Date;

/**
 * Implementation class for the Internship interface.
 * This class provides implementations for various internship-related methods.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class InternshipImpl implements Internship {

  private int id;
  private int contact;
  private int supervisor;
  private String project;
  private Date signatureDate;
  private int versionNumber;

  /**
   * Default constructor for InternshipImpl.
   */
  public InternshipImpl() {
  }

  /**
   * Get the id of the internship.
   *
   * @return The id of the internship
   */
  public int getId() {
    return this.id;
  }

  /**
   * Set the id of the internship.
   *
   * @param id The id to set.
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Get the contact of the internship.
   *
   * @return The contact of the internship
   */
  public int getContact() {
    return this.contact;
  }

  /**
   * Set the contact of the internship.
   *
   * @param contact_id The contact to set.
   */
  public void setContact(int contact_id) {
    this.contact = contact_id;
  }

  /**
   * Get the supervisor of the internship.
   *
   * @return The supervisor of the internship
   */
  public int getSupervisor() {
    return this.supervisor;
  }

  /**
   * Set the supervisor of the internship.
   *
   * @param supervisor_id The supervisor to set.
   */
  public void setSupervisor(int supervisor_id) {
    this.supervisor = supervisor_id;
  }

  /**
   * Get the project of the internship.
   *
   * @return The project of the internship
   */
  public String getProject() {
    return this.project;
  }

  /**
   * Set the project of the internship.
   *
   * @param project The project to set.
   */
  public void setProject(String project) {
    this.project = project;
  }

  /**
   * Get the signature date of the internship.
   *
   * @return The signature date of the internship
   */
  public Date getSignatureDate() {
    return this.signatureDate;
  }

  /**
   * Set the signature date of the internship.
   *
   * @param date The signature date to set.
   */
  public void setSignatureDate(Date date) {
    this.signatureDate = date;
  }


  /**
   * Get the version number of the internship.
   *
   * @return The version number of the internship
   */
  public int getVersionNumber() {
    return versionNumber;
  }


  /**
   * Set the version number of the internship.
   *
   * @param versionNumber The version number to set.
   */
  public void setVersionNumber(int versionNumber) {
    this.versionNumber = versionNumber;
  }
}
