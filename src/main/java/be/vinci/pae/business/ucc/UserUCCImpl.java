package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.UserDTO;
import be.vinci.pae.dal.UserDAO;
import jakarta.inject.Inject;
import java.util.List;

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
  public UserDTO login(String email, String password){
    return null;
  }


}
