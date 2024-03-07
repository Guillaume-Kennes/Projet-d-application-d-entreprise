package be.vinci.pae.dal;

import java.sql.PreparedStatement;

public interface DALBackServices {

  /**
   *
   * @param sql query
   * @return a prepared statement
   */
  PreparedStatement getPreparedStatement(String sql);
}