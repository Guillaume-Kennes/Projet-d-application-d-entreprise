package be.vinci.pae.dal;

import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.domain.UserDTO;
import java.util.List;

public interface UserDAO {

  /**
   * @param
   * @return
   */
  UserDTO getUserByEmail(String email);




  /**
   * @param
   * @return
   */
  UserDTO login(String email, String password);
}
