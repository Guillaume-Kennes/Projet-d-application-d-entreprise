package be.vinci.pae.dal;

import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.domain.UserDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface UserDAO {

  /**
   * @param email the user's email
   * @return the user corresponding to the given email
   */
  UserDTO getUserByEmail(String email) ;

  /**
   * @param resultSet the given resultSet
   * @return the user corresponding to that result set
   */
  UserDTO userInfos(ResultSet resultSet) throws SQLException;
}

