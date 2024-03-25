package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.UserDAO;
import be.vinci.pae.utils.exception.UnauthorizedException;
import jakarta.inject.Inject;
import java.util.List;

/**
 * Implementation of the UserUCC interface.
 * Provides methods related to user operations.
 */
public class UserUCCImpl implements UserUCC {

  @Inject
  private UserDAO userDAO;
  @Inject
  private DALServices dalServices;

  /** Returns the user's data if the login is successful.
   *
   * @param email the user's email
   * @param password the user's password
   *
   * @return the user's data if the login is successful
   */

  public UserDTO login(String email, String password) {

    User userFound = (User) userDAO.getUserByEmail(email);
    if (userFound == null || !userFound.checkPassword(password)) {
      throw new UnauthorizedException("Incorrect Email or Password");
    }
    return userFound;
  }


  /**
   * Returns the user corresponding to the id.
   *
   * @param id the user's id
   *
   * @return the user corresponding to the id
   */
  public UserDTO getUserById(int id) {
    return userDAO.getUserById(id);
  }


  /**
   * Returns the list of all users available in the system.
   *
   * @return A list containing UserDTO objects representing all users.
   *     If no users are found, the list will be empty.
   */
  public List<UserDTO> getAllUsers() {
    dalServices.start();
    try {
      return userDAO.getAllUsers();
    } catch (Exception e) {
      dalServices.rollBack();
      throw e;
    } finally {
      dalServices.commit();
    }
  }

}
