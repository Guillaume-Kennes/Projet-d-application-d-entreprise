package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.dal.UserDAO;
import jakarta.inject.Inject;

public class UserUCCImpl implements UserUCC {

  @Inject
  private UserDAO userDAO;

  /**
   * @param email    the email of the user
   * @param password the password of the user
   * @return the userFound
   */
  public UserDTO login(String email, String password) {
    User userFound = (User) userDAO.getUserByEmail(email);
    System.out.println("USER " + userFound);

    return userFound;
  }
}
