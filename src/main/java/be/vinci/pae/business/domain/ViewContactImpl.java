package be.vinci.pae.business.domain;

/**
 * Implementation class for the ViewContact interface.
 * This class provides implementations for various contact-related methods.
 */
public class ViewContactImpl implements ViewContact {

  private int id;
  private String reasonRefusal;
  private String meetingPlace;
  private ViewCompany company;
  private ViewUEInscription ueInscription;
  private boolean followed;
  private String state;

  /**
   * Default constructor for ViewContactImpl.
   */
  public ViewContactImpl() {
  }

  /**
   * Get the id of the contact.
   *
   * @return The id of the contact
   */
  public int getId() {
    return this.id;
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
   * Get the reason for refusal of the contact.
   *
   * @return The refusal reason of the contact
   */
  public String getReasonRefusal() {
    return this.reasonRefusal;
  }

  /**
   * Set the reason for refusal of the contact.
   *
   * @param reasonRefusal The reason for refusal to set.
   */
  public void setReasonRefusal(String reasonRefusal) {
    this.reasonRefusal = reasonRefusal;
  }

  /**
   * Get the meeting place of the contact.
   *
   * @return The meeting place of the contact
   */
  public String getMeetingPlace() {
    return this.meetingPlace;
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
   * Get the company of the contact.
   *
   * @return The company of the contact
   */
  public ViewCompany getCompany() {

    return this.company;
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
   * Set the UEInscription of the contact.
   *
   * @param ueInscription The UEInscription to set.
   */
  public void setUeInscription(ViewUEInscription ueInscription) {
    this.ueInscription = ueInscription;
  }

  /**
   * Get the state of the contact.
   *
   * @return The state of the contact
   */
  public String getState() {
    return this.state;
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
   * Set the state of the "followed" boolean.
   *
   * @param followed The state to set.
   */
  public void setFollowed(boolean followed) {

    this.followed = followed;
  }

  /**
   * Get the UE inscription of the contact.
   *
   * @return The UE inscription of the contact
   */
  public ViewUEInscription getUeInscription() {

    return this.ueInscription;
  }

  /**
   * Get the value of the followed boolean for the contact.
   *
   * @return The value of the followed boolean
   */
  public boolean isFollowed() {

    return followed;
  }
}
