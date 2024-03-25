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

  /**
   * Authenticates a user by their email and password.
   *
   * @param email The email of the user attempting to log in.
   *
   * @param password The password of the user attempting to log in.
   *
   * @return The UserDTO object representing the authenticated user.
   *
   * @throws UnauthorizedException If the provided email or password is incorrect.
   *
   * @throws Exception If an error occurs during the authentication process.
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
   * Retrieves a UserDTO object by its unique identifier.
   *
   * @param id The unique identifier of the user to retrieve.
   *
   * @return The UserDTO object corresponding to the given identifier.
   *
   * @throws Exception If an error occurs while retrieving the user.
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
   * @throws Exception If an error occurs during registration process.
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
              "The email address must end with @student.vinci.be or @vinci.be");
        } else if (userDTO.getEmail().endsWith("@student.vinci.be")) {
          userDTO.setRole("Student");
        }
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
