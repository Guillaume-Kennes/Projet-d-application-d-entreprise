package be.vinci.pae.dal;

import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.domain.UserDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface UserDAO {

  /** Returns the user which email correspond to the email in parameter
   * @param email the user's email
   *
   * @return the user which email correspond to the email in parameter
   */
  UserDTO getUserByEmail(String email) ;


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

