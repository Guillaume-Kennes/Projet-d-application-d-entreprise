package be.vinci.pae.business.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

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
  private String tradeName;
  private int versionNumber;

  /**
   * Default constructor for ContactImpl.
   */
  public ContactImpl() {
  }

  public int getVersionNumber() {
    return versionNumber;
  }

  public void setVersionNumber(int versionNumber) {
    this.versionNumber = versionNumber;
  }

  /**
   * Get the id of the contact.
   *
   * @return The id of the contact
   */
  @Override
  public int getId() {
    return id;
  }

  /**
   * Set the id of the contact.
   *
   * @param id The id to set.
   */
  @Override
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Get the state of the contact.
   *
   * @return The state of the contact
   */
  @Override
  public String getState() {
    return state;
  }

  /**
   * Set the state of the contact.
   *
   * @param state The state to set.
   */
  @Override
  public void setState(String state) {
    this.state = state;
  }

  /**
   * Retrieves the reason for refusal for the contact.
   *
   * @return The reason for refusal for the contact.
   */
  @Override
  public String getReasonForRefusal() {
    return reasonForRefusal;
  }

  /**
   * Set the reason for refusal of the contact.
   *
   * @param reasonForRefusal The reason to set.
   */
  @Override
  public void setReasonForRefusal(String reasonForRefusal) {
    this.reasonForRefusal = reasonForRefusal;
  }

  /**
   * Get the meeting place of the contact.
   *
   * @return The meeting place of the contact
   */
  @Override
  public String getMeetingPlace() {
    return meetingPlace;
  }

  /**
   * Set the meeting place of the contact.
   *
   * @param meetingPlace The meeting place to set.
   */
  @Override
  public void setMeetingPlace(String meetingPlace) {
    this.meetingPlace = meetingPlace;
  }

  /**
   * Get the UE inscription of the contact.
   *
   * @return The UE inscription of the contact
   */
  @Override
  public UEInscription getInscriptionUE() {
    return this.inscriptionUE;
  }

  /**
   * Set the UE inscription of the contact.
   *
   * @param inscriptionUE The UE inscription to set.
   */
  @Override
  public void setInscriptionUE(UEInscription inscriptionUE) {
    this.inscriptionUE = inscriptionUE;
  }

  /**
   * Get the company of the contact.
   *
   * @return The company of the contact
   */
  @Override
  public CompanyDTO getCompany() {
    return this.company;
  }

  /**
   * Set the company of the contact.
   *
   * @param company The company to set.
   */
  @Override
  public void setCompany(Company company) {
    this.company = company;
  }

  /**
   * Checks if a user is a student.
   *
   * @param idUser The concerned user.
   * @return A boolean stating whether the user is a student
   */
  @Override
  public boolean userRoleIsStudent(int idUser) {
    return false;
  }

  /**
   * Checks if the school year is correct.
   *
   * @return a boolean stating whether the year is correct.
   */
  @Override
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
  @Override
  public UserDTO getUser() {
    return user;
  }

  /**
   * Set the user of the contact.
   *
   * @param user The user to set.
   */
  @Override
  public void setUser(UserDTO user) {
    this.user = user;
  }

  /**
   * Returns the trade name associated with the contact.
   *
   * @return The trade name of the contact.
   */
  @Override
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
  @Override
  public boolean isFollowed() {
    return isFollowed;
  }

  /**
   * Set the value of the isFollowed boolean.
   *
   * @param followed The value to set.
   */
  @Override
  public void setFollowed(boolean followed) {
    isFollowed = followed;
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

}
