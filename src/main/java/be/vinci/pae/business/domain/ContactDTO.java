package be.vinci.pae.business.domain;

/**
 * Interface for Data Transfer Object (DTO) for Contact.
 */
public interface ContactDTO {

  /**
   * Gets the ID of the contact.
   *
   * @return the ID of the contact.
   */
  int getId();

  /**
   * Sets the ID of the contact.
   *
   * @param id the ID of the contact.
   */
  void setId(int id);

  /**
   * Gets the state of the contact.
   *
   * @return the state of the contact.
   */
  String getState();

  /**
   * Sets the state of the contact.
   *
   * @param state the state of the contact.
   */
  void setState(String state);

  /**
   * Gets the enterprise of the contact.
   *
   * @return the enterprise of the contact.
   */
  int getEnterprise();

  /**
   * Sets the enterprise of the contact.
   *
   * @param enterprise the enterprise of the contact.
   */
  void setEnterprise(int enterprise);

  /**
   * Gets the inscription UE of the contact.
   *
   * @return the inscription UE of the contact.
   */
  int getInscriptionUE();

  /**
   * Sets the inscription UE of the contact.
   *
   * @param inscriptionUE the inscription UE of the contact.
   */
  void setInscriptionUE(int inscriptionUE);

  /**
   * Gets the reason for refusal of the contact.
   *
   * @return the reason for refusal of the contact.
   */
  String getReasonForRefusal();

  /**
   * Sets the reason for refusal of the contact.
   *
   * @param reasonForRefusal the reason for refusal of the contact.
   */
  void setReasonForRefusal(String reasonForRefusal);

  /**
   * Checks if the contact is followed.
   *
   * @return true if the contact is followed, false otherwise.
   */
  boolean isFollowed();

  /**
   * Sets the followed status of the contact.
   *
   * @param isFollowed the followed status of the contact.
   */
  void setFollowed(boolean isFollowed);

  /**
   * Gets the meeting place of the contact.
   *
   * @return the meeting place of the contact.
   */
  String getMeetingPlace();

  /**
   * Sets the meeting place of the contact.
   *
   * @param meetingPlace the meeting place of the contact.
   */
  void setMeetingPlace(String meetingPlace);
}
