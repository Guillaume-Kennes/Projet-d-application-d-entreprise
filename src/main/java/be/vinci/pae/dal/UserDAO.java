package be.vinci.pae.dal;

import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.domain.UserDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface UserDAO {

  /**
   * @param
   * @return
   */
  UserDTO getUserByEmail(String email) ;

  UserDTO userInfos(ResultSet resultSet) throws SQLException;

  UserDTO getUserById(int id);
}

