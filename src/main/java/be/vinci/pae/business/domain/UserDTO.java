package be.vinci.pae.business.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = UserImpl.class)
public interface UserDTO {

  /**
   * Returns the user's id
   *
   * @return the user's id
   */
  int getId();

  /**
   * Sets the user's id
   *
   * @param id the user's id to set
   */
  void setId(int id);

  /**
   * Returns the user's email
   *
   * @return the user's email
   */
  String getEmail();

  /**
   * Sets the user's email
   *
   * @param email the user's email to set
   */
  void setEmail(String email);


  /**
   * Returns the user's password
   *
   * @return the user's password
   */
  String getPassword();

  /**
   * Sets the user's password
   *
   * @param password the user's password to set
   */
  void setPassword(String password);

}
