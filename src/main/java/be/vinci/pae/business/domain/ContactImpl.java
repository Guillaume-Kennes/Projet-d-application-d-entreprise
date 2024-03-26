package be.vinci.pae.business.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
class ContactImpl implements Contact {

  private int id;
  private String state;
  private ViewCompany company;
  private ViewUEInscription inscriptionUE;
  private String reasonForRefusal;
  private boolean isFollowed;
  private String meetingPlace;

  /**
   * Default constructor for ContactImpl.
   */
  public ContactImpl() {
  }

  /**
   * Get the id of the contact.
   *
   * @return The id of the contact
   */
  public int getId() {
    return id;
  }

  /**
   * Set the id of the contact.
   *
   * @param id The id to set.
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Get the state of the contact.
   *
   * @return The state of the contact
   */
  public String getState() {
    return state;
  }

  /**
   * Set the state of the contact.
   *
   * @param state The state to set.
   */
  public void setState(String state) {
    this.state = state;
  }

  /**
   * Get the reason for refusal of the contact.
   *
   * @return The reason for refusal of the contact
   */
  public String getReasonForRefusal() {
    return reasonForRefusal;
  }

  /**
   * Set the reason for refusal of the contact.
   *
   * @param reasonForRefusal The reason to set.
   */
  public void setReasonForRefusal(String reasonForRefusal) {
    this.reasonForRefusal = reasonForRefusal;
  }

  /**
   * Get the value of the isFollowed boolean.
   *
   * @return The value of the isFollowed boolean
   */
  public boolean isFollowed() {
    return isFollowed;
  }

  /**
   * Set the value of the isFollowed boolean.
   *
   * @param followed The value to set.
   */
  public void setFollowed(boolean followed) {
    isFollowed = followed;
  }

  /**
   * Get the meeting place of the contact.
   *
   * @return The meeting place of the contact
   */
  public String getMeetingPlace() {
    return meetingPlace;
  }

  /**
   * Set the meeting place of the contact.
   *
   * @param meetingPlace The meeting place to set.
   */
  public void setMeetingPlace(String meetingPlace) {
    this.meetingPlace = meetingPlace;
  }

  /**
   * Set the company of the contact.
   *
   * @param company The company to set.
   */
  public void setCompany(ViewCompany company) {
    this.company = company;
  }

  /**
   * Get the UE inscription of the contact.
   *
   * @return The UE inscription of the contact
   */
  public ViewUEInscription getInscriptionUE() {
    return this.inscriptionUE;
  }

  /**
   * Set the UE inscription of the contact.
   *
   * @param inscriptionUE The UE inscription to set.
   */
  public void setInscriptionUE(ViewUEInscription inscriptionUE) {
    this.inscriptionUE = inscriptionUE;
  }

  /**
   * Get the company of the contact.
   *
   * @return The company of the contact
   */
  public ViewCompanyDTO getCompany() {
    return this.company;
  }

  /**
   * Checks if a user is a student.
   *
   * @param idUser The concerned user.
   *
   * @return A boolean stating whether the user is a student
   */
  public boolean userRoleIsStudent(int idUser) {
    return false;
  }

  /**
   * Checks if the school year is correct.
   *
   * @return a boolean stating whether the year is correct.
   */
  public boolean correctSchoolYear() {
    return false;
  }
}
