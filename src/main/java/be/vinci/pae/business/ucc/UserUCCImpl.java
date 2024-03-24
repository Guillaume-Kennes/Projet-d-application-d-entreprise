package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.UserDAO;
import be.vinci.pae.utils.exception.UnauthorizedException;
import jakarta.inject.Inject;

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
    // start mais cest k
    dalServices.start();

    try {
      User userFound = (User) userDAO.getUserByEmail(email);
      if (userFound == null || !userFound.checkPassword(password)) {
        throw new UnauthorizedException("Incorrect Email or Password");
      }

      return userFound;
    } catch (Exception e) {
      dalServices.rollBack();
      throw e;

    } finally {
      dalServices.commit();
    }

  }


  /**
   * Returns the user corresponding to the id.
   *
   * @param id the user's id
   *
   * @return the user corresponding to the id
   */
  public UserDTO getUserById(int id) {
    dalServices.start();
    try {
      UserDTO userDTO = userDAO.getUserById(id);
      return userDTO;
    } catch (Exception e) {
      dalServices.rollBack();
      throw e;
    } finally {
      dalServices.commit();
    }
  }

  /**
   * Registers a new user in the system.
   *
   * @param userDTO The user data transfer object containing user information.
   *
   * @return The registered user data transfer object.
   *
   * @throws UnauthorizedException If the email already exists in the database
   *     or if the email address does not end with "@student.vinci.be" or "@vinci.be".
   *
   * @throws Exception             If an error occurs during registration process.
   */
  public UserDTO register(UserDTO userDTO) {
    dalServices.start();

    User user = (User) userDTO;

    if (userDAO.getUserByEmail(userDTO.getEmail()) != null) {
      throw new UnauthorizedException("This email already exists in database");
    } else {
      try {
        if (!userDTO.getEmail().endsWith("@vinci.be")
            && !userDTO.getEmail().endsWith("@student.vinci.be")) {
          throw new UnauthorizedException(
              "The email address should end with @student.vinci.be or @vinci.be");
        }
        user.setRole(userDTO.getRole());
        userDTO.setPassword(user.hashPassword(userDTO.getPassword()));
        return userDAO.register(userDTO);

      } catch (Exception e) {
        dalServices.rollBack();
        throw e;
      } finally {
        dalServices.commit();
      }
    }
  }
}
