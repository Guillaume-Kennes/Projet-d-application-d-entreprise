package be.vinci.pae.business.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents an implementation of the ContactDTO interface. It provides getters and
 * setters for contact attributes.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ContactImpl implements ContactDTO {

  private int id;
  private String state;
  private int enterprise;
  @JsonProperty("inscription_ue")
  private int inscriptionUe;
  private String reasonForRefusal;
  private boolean isFollowed;
  private String meetingPlace;
  private int userId;
  private String tradeName;

  /**
   * Retrieves the ID of the contact.
   *
   * @return The ID of the contact.
   */
  @Override
  public int getId() {
    return id;
  }

  /**
   * Sets the ID of the contact.
   *
   * @param id The new ID of the contact.
   */
  @Override
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Retrieves the state of the contact.
   *
   * @return The state of the contact.
   */
  @Override
  public String getState() {
    return state;
  }

  /**
   * Sets the state of the contact.
   *
   * @param state The new state of the contact.
   */
  @Override
  public void setState(String state) {
    this.state = state;
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
   * Retrieves the ID of the user associated with the contact inscription.
   *
   * @return The ID of the user associated with the contact inscription.
   */
  @Override
  public int getInscriptionUe() {
    return inscriptionUe;
  }

  /**
   * Sets the ID of the user associated with the contact inscription.
   *
   * @param inscriptionUe The new ID of the user associated with the contact inscription.
   */
  @Override
  public void setInscriptionUe(int inscriptionUe) {
    this.inscriptionUe = inscriptionUe;
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
   * Sets the reason for refusal for the contact.
   *
   * @param reasonForRefusal The new reason for refusal for the contact.
   */
  @Override
  public void setReasonForRefusal(String reasonForRefusal) {
    this.reasonForRefusal = reasonForRefusal;
  }

  /**
   * Retrieves whether the contact is followed or not.
   *
   * @return true if the contact is followed, false otherwise.
   */
  @Override
  public boolean getIsFollowed() {
    return isFollowed;
  }

  /**
   * Sets whether the contact is followed or not.
   *
   * @param isFollowed true if the contact is followed, false otherwise.
   */
  @Override
  public void setIsFollowed(boolean isFollowed) {
    this.isFollowed = isFollowed;
  }

  /**
   * Retrieves the meeting place for the contact.
   *
   * @return The meeting place for the contact.
   */
  @Override
  public String getMeetingPlace() {
    return meetingPlace;
  }

  /**
   * Sets the meeting place for the contact.
   *
   * @param meetingPlace The new meeting place for the contact.
   */
  @Override
  public void setMeetingPlace(String meetingPlace) {
    this.meetingPlace = meetingPlace;
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

  @Override
  public String toString() {
    return "ContactImpl{"
        + "id=" + id
        + ", state='" + state + '\''
        + ", enterprise=" + enterprise
        + ", inscriptionUe=" + inscriptionUe
        + ", reasonForRefusal='" + reasonForRefusal
        + '\'' + ", isFollowed=" + isFollowed
        + ", meetingPlace='" + meetingPlace + '\''
        + '}';
  }
}
