package be.vinci.pae.dal;

import java.sql.PreparedStatement;


/**
 *
 */
public interface DALServices {

  /**
   *
   * @param sql query
   * @return
   */
  PreparedStatement getPreparedStatement(String sql);
}