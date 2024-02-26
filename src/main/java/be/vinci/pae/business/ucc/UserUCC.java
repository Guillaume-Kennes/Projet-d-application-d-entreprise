package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.domain.UserDTO;
import java.util.List;

public interface UserUCC {

<<<<<<< HEAD
  /**
   * Returns the user's data if the login is successful
   *
   * @param email the user's email
   * @param password the user's password
   *
   * @return the user's data if the login is successful
   */
  UserDTO login(String email, String password);

=======
 UserDTO login(String email, String password);
>>>>>>> 4811fccfeb4df4d53b959ce47e93a56ce551ec4f

}
