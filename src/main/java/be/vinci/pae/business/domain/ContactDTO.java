package be.vinci.pae.business.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Interface for Data Transfer Object (DTO) for Contact.
 */
@JsonDeserialize(as = ContactImpl.class)
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
  CompanyDTO getCompany();

  /**
   * Sets the enterprise of the contact.
   *
   * @param enterprise the enterprise of the contact.
   */
  void setCompany(Company enterprise);

  /**
   * Gets the inscription UE of the contact.
   *
   * @return the inscription UE of the contact.
   */
  UEInscription getInscriptionUE();

  /**
   * Sets the inscription UE of the contact.
   *
   * @param inscriptionUE the inscription UE of the contact.
   */
  void setInscriptionUE(UEInscription inscriptionUE);

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
   * Sets the meeting place for the contact.
   *
   * @param meetingPlace the new meeting place for the contact.
   */
  void setMeetingPlace(String meetingPlace);

  /**
   * Checks if the contact is in the taken state.
   *
   * @return true if the contact is taken, false otherwise.
   */
  boolean stateIsTaken();

  /**
   * Checks if the contact is in the initiated state.
   *
   * @return true if the contact is initiated, false otherwise.
   */
  boolean stateIsInitiated();

  /**
   * Gets the user of the contact.
   *
   * @return the user of the contact.
   */
  UserDTO getUser();

  /**
   * Sets the user of the contact.
   *
   * @param user the user of the contact.
   */
  void setUser(UserDTO user);

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

  /**
   * Obtient le numéro de version de l'entité. Cette méthode retourne le numéro de version de
   * l'entité.
   *
   * @return Le numéro de version de l'entité.
   */
  int getVersionNumber();

  /**
   * Définit le numéro de version de l'entité. Cette méthode définit le numéro de version de
   * l'entité avec la valeur spécifiée.
   *
   * @param versionNumber Le numéro de version à définir pour l'entité.
   */
  void setVersionNumber(int versionNumber);
}
