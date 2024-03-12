package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.dal.UserDAO;
import be.vinci.pae.utils.exception.UnauthorizedException;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.sql.SQLException;

public class UserUCCImpl implements UserUCC {
  @Inject
  private UserDAO userDAO;

  /**
   * Returns the user's data if the login is successful
   *
   * @param email the user's email
   * @param password the user's password
   *
   * @return the user's data if the login is successful
   */

  public UserDTO login(String email, String password) {
    User userFound = (User) userDAO.getUserByEmail(email);

    if(userFound == null || !userFound.checkPassword(password)) {
      throw new UnauthorizedException("Incorrect Email or Password");
    }
    return userFound;
  }


  /**
   * Returns the user corresponding to the id
   *
   * @param id the user's id
   *
   * @return the user corresponding to the id
   */
  public UserDTO getUserById(int id){
    return userDAO.getUserById(id);
  }




}
