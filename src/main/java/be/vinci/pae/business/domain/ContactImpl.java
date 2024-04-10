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
  private int enterprise;
  private int userId;
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


  public boolean initieState(ContactDTO contact) {
    return contact.getState().equals("initié");
  }

  public boolean prisState(ContactDTO contact) {
    return contact.getState().equals("pris");
  }

  public boolean isFollowed(ContactDTO contact) {
    return contact.isFollowed();
  }
}
