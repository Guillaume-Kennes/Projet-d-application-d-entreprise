package be.vinci.pae.dal;

import java.sql.Connection;

/**
 * Interface defining Data Access Layer (DAL) services.
 * These services provide methods for managing database transactions.
 */
public interface DALServices {

  /**
   * Start a database connection.
   * @return The started database connection.
   */
  Connection start();

  /**
   * Commit a database transaction.
   * @param connection The database connection to commit the transaction on.
   */
  void commit(Connection connection);

  /**
   * Roll back a database transaction.
   * @param connection The database connection to roll back the transaction on.
   */
  void rollBack(Connection connection);
}
