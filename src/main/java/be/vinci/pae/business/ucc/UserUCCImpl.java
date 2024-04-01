package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.UserDAO;
import be.vinci.pae.utils.exception.BusinessException;
import be.vinci.pae.utils.exception.ConflictException;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response.Status;
import java.util.List;

/**
 * Implementation of the UserUCC interface. Provides methods related to user operations.
 */
public class UserUCCImpl implements UserUCC {

  @Inject
  private UserDAO userDAO;
  @Inject
  private DALServices dalServices;


  /**
   * Authenticates a user by their email and password.
   *
   * @param email    The email of the user attempting to log in.
   * @param password The password of the user attempting to log in.
   * @return The UserDTO object representing the authenticated user.
   * @throws BusinessException If the provided email or password is incorrect.
   */
  public UserDTO login(String email, String password) {
    dalServices.start();
    try {
      User userFound = (User) userDAO.getUserByEmail(email);
      if (userFound == null || !userFound.checkPassword(password)) {
        dalServices.rollBack();
        throw new BusinessException("Incorrect Email or Password");
      }
      dalServices.commit();
      return userFound;
    } catch (Exception e) {
      dalServices.rollBack();
      throw e;
    }
  }


  /**
   * Retrieves a UserDTO object by its unique identifier.
   *
   * @param id The unique identifier of the user to retrieve.
   * @return The UserDTO object corresponding to the given identifier.
   */
  public UserDTO getUserById(int id) {
    dalServices.start();
    try {
      UserDTO userDTO = userDAO.getUserById(id);
      dalServices.commit();
      return userDTO;
    } catch (Exception e) {
      dalServices.rollBack();
      throw e;
    }
  }


  /**
   * Returns the list of all users available in the system.
   *
   * @return A list containing UserDTO objects representing all users. If no users are found, the
   *     list will be empty.
   */
  public List<UserDTO> getAllUsers() {
    dalServices.start();
    try {
      List<UserDTO> users = userDAO.getAllUsers();
      dalServices.commit();
      return users;
    } catch (Exception e) {
      dalServices.rollBack();
      throw e;
    }
  }


  /**
   * Registers a new user in the system.
   *
   * @param userDTO The user data transfer object containing user information.
   * @return The registered user data transfer object.
   * @throws BusinessException If the email already exists in the database or if the email
   *                               address does not end with "@student.vinci.be" or "@vinci.be".
   */
  public UserDTO register(UserDTO userDTO) {
    dalServices.start();

    User user = (User) userDTO;

    if (userDAO.getUserByEmail(userDTO.getEmail()) != null) {
      dalServices.rollBack();
      throw new ConflictException("This email already exists in database");
    } else {
      try {
        if (!userDTO.getEmail().endsWith("@vinci.be")
            && !userDTO.getEmail().endsWith("@student.vinci.be")) {
          dalServices.rollBack();
          throw new BusinessException(
              "The email address must end with @student.vinci.be or @vinci.be", Status.BAD_REQUEST);
        } else if (userDTO.getEmail().endsWith("@student.vinci.be")) {
          userDTO.setRole("Etudiant");
        }
        userDTO.setPassword(user.hashPassword(userDTO.getPassword()));
        UserDTO registeredUser = userDAO.register(userDTO);
        dalServices.commit();
        return registeredUser;

      } catch (Exception e) {
        dalServices.rollBack();
        throw e;
      }
    }
  }
}
