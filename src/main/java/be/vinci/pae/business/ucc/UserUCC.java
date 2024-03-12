package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.UserDTO;

public interface UserUCC {

  /**
   * Returns the user's data if the login is successful
   *
   * @param email    the user's email
   * @param password the user's password
   * @return the user's data if the login is successful
   */
  UserDTO login(String email, String password);


  /**
   * Returns the user corresponding to the id
   *
   * @param id the user's id
   *
   * @return the user corresponding to the id
   */
  UserDTO getUserById(int id);
}
