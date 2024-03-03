package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.dal.UserDAO;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response.Status;

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
    System.out.println("USER (from UserUCCImpl) " + userFound);
    // need token before this works
    if (userFound == null) {
      throw new WebApplicationException("Incorrect Email or Password", Status.UNAUTHORIZED);
    }

    if (!userFound.checkPassword(password)) {
      throw new WebApplicationException("Incorrect Email or Password", Status.UNAUTHORIZED);
    }

    return userFound;
  }
}
