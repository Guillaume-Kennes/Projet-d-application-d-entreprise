package be.vinci.pae.business.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Date;

/**
 * Represents a user data transfer object (DTO) with various properties.
 * This interface defines methods for accessing and modifying user-related information.
 */
@JsonDeserialize(as = UserImpl.class)
public interface UserDTO {

  /**
   * Returns the user's email.
   *
   * @return the user's email
   */
  String getEmail();

  /**
   * Sets the user's email.
   *
   * @param email the user's email to set
   */
  void setEmail(String email);

  /**
   * Returns the user's password.
   *
   * @return the user's password
   */
  String getPassword();

  /**
   * Sets the user's password.
   *
   * @param password the user's password to set
   */
  void setPassword(String password);

  /**
   * Returns the user's last name.
   *
   * @return the user's last name
   */
  String getLastName();

  /**
   * Sets the user's lastname.
   *
   * @param lastName the user's lastname to set
   */
  void setLastName(String lastName);

  /**
   * Returns the user's first name.
   *
   * @return the user's first name
   */
  String getFirstName();

  /**
   * Sets the user's first name.
   *
   * @param firstName the user's first name to set
   */
  void setFirstName(String firstName);

  /**
   * Returns the user's phone number.
   *
   * @return the user's phone number
   */
  String getPhoneNumber();

  /**
   * Sets the user's phone number.
   *
   * @param phoneNumber the user's phone number to set
   */
  void setPhoneNumber(String phoneNumber);

  /**
   * Returns the user's registration date.
   *
   * @return the user's registration date
   */
  Date getRegistrationDate();

  /**
   * Sets the user's registration date.
   *
   * @param registrationDate the user's registration date to set
   */
  void setRegistrationDate(Date registrationDate);

  /**
   * Returns the user's role.
   *
   * @return the user's role
   */
  String getRole();

  /**
   * Sets the user's role.
   *
   * @param role the user's role to set
   */
  void setRole(String role);

  /**
   * Returns the user's id.
   *
   * @return the user's id
   */
  int getId();

  /**
   * Sets the ID for an object.
   *
   * @param id The unique identifier to set.
   */
  void setId(int id);

  /**
   * Retrieves the version number.
   *
   * @return The current version number.
   */
  int getVersionNumber();

  /**
   * Sets the version number to the specified value.
   *
   * @param versionNumber The version number to set.
   * @return The updated version number after setting.
   */
  int setVersionNumber(int versionNumber);
}
