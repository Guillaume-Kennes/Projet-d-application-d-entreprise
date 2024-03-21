package be.vinci.pae.business.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Implementation class for the Internship interface.
 * This class provides implementations for various internship-related methods.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class InternshipImpl implements Internship{

  private int id;
  private ViewContact contact;
  private InternshipSupervisor supervisor;
  private String project, signatureDate;

  /**
   * Default constructor for InternshipImpl.
   */
  public InternshipImpl(){
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
  public ViewContact getContact() {
    return this.contact;
  }

  /**
   * Set the contact of the internship.
   *
   * @param contact The contact to set.
   */
  public void setContact(ViewContact contact){
    this.contact = contact;
  }

  /**
   * Get the supervisor of the internship.
   *
   * @return The supervisor of the internship
   */
  public InternshipSupervisor getSupervisor() {
    return this.supervisor;
  }

  /**
   * Set the supervisor of the internship.
   *
   * @param supervisor The supervisor to set.
   */
  public void setSupervisor(InternshipSupervisor supervisor) {
    this.supervisor = supervisor;
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
  public String getDate() {
    return this.signatureDate;
  }

  /**
   * Set the signature date of the internship.
   *
   * @param date The signature date to set.
   */
  public void setDate(String date) {
    this.signatureDate = date;
  }
}
