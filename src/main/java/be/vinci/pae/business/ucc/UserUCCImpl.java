package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.dal.UserDAO;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.sql.SQLException;

public class UserUCCImpl implements UserUCC {
  @Inject
  private UserDAO userDAO;

  /**
   *
   *
   * @param email
   * @param password
   *
   * @return
   */
  public UserDTO login(String email, String password) {
    User userFound = (User) userDAO.getUserByEmail(email);

    if(userFound == null || !userFound.checkPassword(password)) {
      throw new IllegalArgumentException("Incorrect Email or Password");
    }


    return userFound;
  }




}
