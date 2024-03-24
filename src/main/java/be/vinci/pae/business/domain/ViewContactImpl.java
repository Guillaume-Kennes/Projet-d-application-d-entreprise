package be.vinci.pae.business.domain;

/**
 * Implementation class for the ViewContact interface.
 * This class provides implementations for various contact-related methods.
 */
public class ViewContactImpl implements ViewContact {

  /**
   * Enum for containing the different states of a contact.
   * This enum contains all the states a contact is allowed to be in.
   */
  public enum State {
    ABANDONNE, REFUSE, PRIS, INITIE, SUSPENDU, ACCEPTE
  }

  /**
   * Enum for containing the different meeting places of a contact.
   * This enum contains the 2 different meeting places possible for a contact.
   */
  public enum MeetingPlace {
    ENTREPRISE, DISTANCIEL
  }

  private int id;
  private String reasonRefusal;
  private MeetingPlace meetingPlace;
  private ViewCompany company;
  private ViewUEInscription ueInscription;
  private boolean followed;
  private State state;

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
   * Set the reason for refusal of the contact.
   *
   * @param reasonRefusal The reason for refusal to set.
   */
  public void setReasonRefusal(String reasonRefusal) {
    this.reasonRefusal = reasonRefusal;
  }

  /**
   * Set the meeting place of the contact.
   *
   * @param meetingPlace The meeting place to set.
   */
  public void setMeetingPlace(String meetingPlace) {
    try {
      MeetingPlace meet = MeetingPlace.valueOf(meetingPlace);
      this.meetingPlace = meet;
    } catch (IllegalArgumentException e) {
      throw e;
    }
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
    return this.state.name();
  }

  /**
   * Set the state of the contact.
   *
   * @param state The state to set.
   */
  public void setState(String state) {
    try {
      State state1 = State.valueOf(state);
      this.state = state1;
    } catch (IllegalArgumentException e) {
      throw e;
    }
  }

  /**
   * Set the state of the "followed" boolean.
   *
   * @param followed The state to set.
   */
  public void setFollowed(boolean followed) {
    this.followed = followed;
  }
}
