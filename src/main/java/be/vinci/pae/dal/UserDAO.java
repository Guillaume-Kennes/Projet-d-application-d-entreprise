package be.vinci.pae.dal;

import be.vinci.pae.business.domain.UserDTO;
import java.sql.ResultSet;

public interface UserDAO {

  /** Returns the user corresponding to the given email
   * @param email the user's email
   * @return the user corresponding to the given email
   */
  UserDTO getUserByEmail(String email);


  /**
   * @param resultSet the given resultSet
   * @return the user corresponding to that result set
   */
  UserDTO userInfos(ResultSet resultSet);



  /**
   * Returns the user corresponding to the id
   *
   * @param id the user's id
   *
   * @return the user corresponding to the id
   */
  UserDTO getUserById(int id);

}

