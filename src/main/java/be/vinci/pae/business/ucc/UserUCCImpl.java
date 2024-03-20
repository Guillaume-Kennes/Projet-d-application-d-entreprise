package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.UserDAO;
import be.vinci.pae.utils.exception.UnauthorizedException;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.util.Date;

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
    try{
      dalServices.start();
      User userFound = (User) userDAO.getUserByEmail(email);
      if (userFound == null || !userFound.checkPassword(password)) {
        throw new UnauthorizedException("Incorrect Email or Password");
      }

      dalServices.commit();
      return userFound;
    }catch(Exception e){
      dalServices.rollBack();
      throw e;
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
    try{
      dalServices.start();
      UserDTO userDTO = userDAO.getUserById(id);
      dalServices.commit();
      return userDTO;
    }catch(Exception e){
      dalServices.rollBack();
      throw e;
    }
  }


  public UserDTO register(UserDTO userDTO){
    //userDTO.setPassword(User.hashPassword(userDTO.getPassword()));
    try{
      dalServices.start();
      return userDAO.register(userDTO);
    }catch(Exception e){
      dalServices.rollBack();
      throw e;
    }
  }

}
