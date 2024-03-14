package be.vinci.pae.dal;

import java.sql.PreparedStatement;

/**
 * Interface for Data Access Layer (DAL) back-end services.
 * This interface provides methods for obtaining prepared statements for database queries.
 */
public interface DALBackServices {

  /**
   * Retrieves a prepared statement for the specified SQL query.
   *
   * @param sql The SQL query string for which to obtain the prepared statement.
   *
   * @return A PreparedStatement object for the specified SQL query.
   */
  PreparedStatement getPreparedStatement(String sql);
}