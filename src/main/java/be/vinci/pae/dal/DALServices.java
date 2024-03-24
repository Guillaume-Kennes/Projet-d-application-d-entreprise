package be.vinci.pae.dal;

/**
 * Interface defining Data Access Layer (DAL) services.
 * These services provide methods for managing database transactions.
 */
public interface DALServices {

  /**
   * Start a database connection.
   *
   * @return The started database connection.
   */
  void start();

  /**
   * Commit a database transaction.
   */
  void commit();

  /**
   * Roll back a database transaction.
   */
  void rollBack();
}
