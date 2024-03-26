package be.vinci.pae.business.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * This interface represents a Data Transfer Object (DTO) for a contact. It provides methods to
 * access and manipulate contact data.
 */
@JsonDeserialize(as = ContactImpl.class)
public interface ContactDTO {

  /**
   * Retrieves the ID of the contact.
   *
   * @return the ID of the contact.
   */
  int getId();

  /**
   * Sets the ID of the contact.
   *
   * @param id the new ID of the contact.
   */
  void setId(int id);

  /**
   * Returns the state of the contact.
   *
   * @return The state of the contact.
   */
  String getState();

  /**
   * Sets the state of the contact.
   *
   * @param state the new state of the contact.
   */
  void setState(String state);

  /**
   * Returns the ID of the enterprise associated with the contact.
   *
   * @return The ID of the enterprise associated with the contact.
   */
  int getEnterprise();

  /**
   * Sets the ID of the enterprise associated with the contact.
   *
   * @param enterprise the new ID of the enterprise associated with the contact.
   */
  void setEnterprise(int enterprise);

  /**
   * Returns the ID of the user associated with the contact inscription.
   *
   * @return The ID of the user associated with the contact inscription.
   */
  int getInscriptionUe();

  /**
   * Sets the ID of the user associated with the contact inscription.
   *
   * @param inscriptionUe the new ID of the user associated with the contact inscription.
   */
  void setInscriptionUe(int inscriptionUe);

  /**
   * Returns the reason for refusal for the contact.
   *
   * @return The reason for refusal for the contact.
   */
  String getReasonForRefusal();

  /**
   * Sets the reason for refusal for the contact.
   *
   * @param reasonForRefusal the new reason for refusal for the contact.
   */
  void setReasonForRefusal(String reasonForRefusal);

  /**
   * Returns whether the contact is followed or not.
   *
   * @return true if the contact is followed, false otherwise.
   */
  boolean getIsFollowed();

  /**
   * Sets whether the contact is followed or not.
   *
   * @param isFollowed true if the contact is followed, false otherwise.
   */
  void setIsFollowed(boolean isFollowed);

  /**
   * Returns the meeting place for the contact.
   *
   * @return The meeting place for the contact.
   */
  String getMeetingPlace();

  /**
   * Sets the meeting place for the contact.
   *
   * @param meetingPlace the new meeting place for the contact.
   */
  void setMeetingPlace(String meetingPlace);

  /**
   * Returns the ID of the user associated with the contact.
   *
   * @return The ID of the user associated with the contact.
   */
  int getUserId();

  /**
   * Sets the ID of the user associated with the contact.
   *
   * @param userId the new ID of the user associated with the contact.
   */
  void setUserId(int userId);

  // New method to retrieve trade name

  /**
   * Returns the trade name associated with the contact.
   *
   * @return The trade name associated with the contact.
   */
  String getTradeName();

  /**
   * Sets the trade name associated with the contact.
   *
   * @param tradeName the new trade name associated with the contact.
   */
  void setTradeName(String tradeName);
}
