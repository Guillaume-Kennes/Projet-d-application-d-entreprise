package be.vinci.pae.business.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Represents a contact data transfer object (DTO) with various properties.
 * This interface defines methods for accessing and modifying contact-related information.
 */
@JsonDeserialize(as = UserImpl.class)
public interface ViewContactDTO {

  /**
   * Returns the contact's id.
   *
   * @return the contact's id
   */
  int getId();

  /**
   * Sets the contact's id.
   *
   * @param id the contact's id to set
   */
  void setId(int id);

  /**
   * Sets the contact's reason for refusal.
   *
   * @param reasonRefusal the contact's reason for refusal
   */
  void setReasonRefusal(String reasonRefusal);

  /**
   * Sets the contact's meeting place
   *
   * @param meetingPlace the contact's meeting place to set
   */
  void setMeetingPlace(String meetingPlace);

  /**
   * Returns the contact's company
   *
   * @return the contact's company
   */
  ViewCompany getCompany();

  /**
   * Sets the contact's company.
   *
   * @param company the contact's company to set
   */
  void setCompany(ViewCompany company);

  /**
   * Sets the contact's UEInscription.
   *
   * @param ueInscription the contact's UEInscription to set
   */
  void setUeInscription(ViewUEInscription ueInscription);

  /**
   * Returns the contact's state
   *
   * @return the contact's state
   */
  String getState();

  /**
   * Sets the contact's state.
   *
   * @param state the contact's state to set
   */
  void setState(String state);

  /**
   * Sets the boolean followed.
   *
   * @param followed the new boolean
   */
  void setFollowed(boolean followed);
}
