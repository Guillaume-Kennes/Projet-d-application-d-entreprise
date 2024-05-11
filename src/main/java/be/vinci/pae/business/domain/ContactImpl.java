package be.vinci.pae.business.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Implementation class for the Contact interface. This class provides implementations for various
 * contact-related methods.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
class ContactImpl implements Contact {

  private int id;
  private String state;
  private Company company;
  private UEInscription inscriptionUE;
  private String reasonForRefusal;
  private boolean isFollowed;
  private String meetingPlace;
  private UserDTO user;
  private int enterprise;
  private int userId;
  private String tradeName;
  private int versionNumber;

  /**
   * Default constructor for ContactImpl.
   */
  public ContactImpl() {
  }

  /**
   * Get the version number of the contact.
   *
   * @return The version number of the contact
   */
  public int getVersionNumber() {
    return versionNumber;
  }

  /**
   * Set the version number of the contact.
   *
   * @param versionNumber The version number to set.
   */
  public void setVersionNumber(int versionNumber) {
    this.versionNumber = versionNumber;
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
   * Retrieves the reason for refusal for the contact.
   *
   * @return The reason for refusal for the contact.
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
   * Get the UE inscription of the contact.
   *
   * @return The UE inscription of the contact
   */
  public UEInscription getInscriptionUE() {
    return this.inscriptionUE;
  }

  /**
   * Set the UE inscription of the contact.
   *
   * @param inscriptionUE The UE inscription to set.
   */
  public void setInscriptionUE(UEInscription inscriptionUE) {
    this.inscriptionUE = inscriptionUE;
  }

  /**
   * Get the company of the contact.
   *
   * @return The company of the contact
   */
  public CompanyDTO getCompany() {
    return this.company;
  }

  /**
   * Set the company of the contact.
   *
   * @param company The company to set.
   */
  public void setCompany(Company company) {
    this.company = company;
  }

  /**
   * Checks if a user is a student.
   *
   * @param idUser The concerned user.
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

  /**
   * Checks if the state is taken.
   *
   * @return a boolean stating whether the contact is taken.
   */
  public boolean stateIsTaken() {
    if (this.state.equals("pris")) {
      return true;
    }
    return false;
  }

  /**
   * Checks if the state is initiated.
   *
   * @return a boolean stating whether the contact is initiated.
   */
  public boolean stateIsInitiated() {
    if (this.state.equals("initié")) {
      return true;
    }
    return false;
  }

  /**
   * Get the user of the contact.
   *
   * @return The user of the contact
   */
  public UserDTO getUser() {
    return user;
  }

  /**
   * Set the user of the contact.
   *
   * @param user The user to set.
   */
  public void setUser(UserDTO user) {
    this.user = user;
  }


  /**
   * Retrieves the ID of the enterprise associated with the contact.
   *
   * @return The ID of the enterprise associated with the contact.
   */
  @Override
  public int getEnterprise() {
    return enterprise;
  }

  /**
   * Sets the ID of the enterprise associated with the contact.
   *
   * @param enterprise The new ID of the enterprise associated with the contact.
   */
  @Override
  public void setEnterprise(int enterprise) {
    this.enterprise = enterprise;
  }

  /**
   * Retrieves the ID of the user associated with the contact.
   *
   * @return The ID of the user associated with the contact.
   */
  @Override
  public int getUserId() {
    return userId;
  }

  /**
   * Sets the ID of the user associated with the contact.
   *
   * @param userId The new ID of the user associated with the contact.
   */
  @Override
  public void setUserId(int userId) {
    this.userId = userId;
  }

  /**
   * Returns the trade name associated with the contact.
   *
   * @return The trade name of the contact.
   */
  public String getTradeName() {
    return tradeName;
  }

  /**
   * Sets the trade name associated with the contact.
   *
   * @param tradeName The new trade name to be set.
   */
  public void setTradeName(String tradeName) {
    this.tradeName = tradeName;
  }

  /**
   * Checks if the state of the contact is "initié". This method checks if the state of the given
   * contact is "initié".
   *
   * @param contact The contact to check.
   * @return true if the state of the contact is "initié", false otherwise.
   */
  public boolean initieState(ContactDTO contact) {
    return contact.getState().equals("initié");
  }

  /**
   * Checks if the state of the contact is "pris". This method checks if the state of the given
   * contact is "pris".
   *
   * @param contact The contact to check.
   * @return true if the state of the contact is "pris", false otherwise.
   */
  public boolean prisState(ContactDTO contact) {
    return contact.getState().equals("pris");
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
   * Checks if the contact is followed. This method checks if the given contact is followed.
   *
   * @param contact The contact to check.
   * @return true if the contact is followed, false otherwise.
   */
  public boolean isFollowed(ContactDTO contact) {
    return contact.isFollowed();
  }

  /**
   * Set the value of the isFollowed boolean.
   *
   * @param followed The value to set.
   */
  public void setFollowed(boolean followed) {
    isFollowed = followed;
  }
}
