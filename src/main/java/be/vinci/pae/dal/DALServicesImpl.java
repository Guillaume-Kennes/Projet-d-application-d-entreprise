package be.vinci.pae.dal;

import be.vinci.pae.utils.Config;
import be.vinci.pae.utils.exception.FatalException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 * Implementation of the DALServices interface. Manages database connections and provides methods
 * for database operations.
 */
public class DALServicesImpl implements DALBackServices, DALServices {

  /** ThreadLocal variable to hold connection objects for each thread. */
  private final ThreadLocal<Connection> connectionThread;

  /** ThreadLocal variable to keep track of the number of threads. */
  private final ThreadLocal<Integer> counterThreads;

  /** DataSource object for managing database connections. */
  private final BasicDataSource connectionBDS;


  /**
   * Constructs a new DALServicesImpl object.
   * Initializes ThreadLocal variables and sets up database connection settings.
   */
  public DALServicesImpl() {
    connectionThread = new ThreadLocal<>();
    counterThreads = new ThreadLocal<>();
    connectionBDS = new BasicDataSource();

    connectionBDS.setUrl(Config.getProperty("DatabaseFilePath"));
    connectionBDS.setUsername(Config.getProperty("DatabaseUser"));
    connectionBDS.setPassword(Config.getProperty("DatabasePassword"));
    connectionBDS.setDriverClassName("org.postgresql.Driver");
    connectionBDS.setMaxTotal(5);
  }


  /**
   * Retrieves a PreparedStatement object for the provided SQL query.
   *
   * @param sql The SQL query.
   * @param primaryKey A boolean indicating whether the generated keys are required.
   *
   * @return A PreparedStatement object.
   *
   * @throws FatalException if a SQLException occurs.
   */
  public PreparedStatement getPreparedStatement(String sql, boolean primaryKey) {
    try {
      return connectionThread.get().prepareStatement(sql, primaryKey
          ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
    } catch (SQLException e) {
      throw new FatalException(e);
    }
  }

  /**
   * Overloaded method to retrieve a PreparedStatement object without considering primary keys.
   *
   * @param sql The SQL query.
   *
   * @return A PreparedStatement object.
   */
  public PreparedStatement getPreparedStatement(String sql) {
    return getPreparedStatement(sql, false);
  }


  /**
   * Starts a new database transaction.
   * If no transaction is active for the current thread, a new connection is established.
   * Otherwise, the counter for the active transaction is incremented.
   *
   * @throws FatalException if a SQLException occurs.
   */
  public void start() {
    if (counterThreads.get() == null) {
      try {
        counterThreads.set(1);
        Connection connection = connectionBDS.getConnection();
        connection.setAutoCommit(false);
        connectionThread.set(connection);
      } catch (SQLException e) {
        throw new FatalException(e);
      }
    } else {
      counterThreads.set(counterThreads.get() + 1);
    }
  }


  /**
   * Commits the current transaction.
   * If the transaction is the only active one, the connection is closed after commit.
   *
   * @throws FatalException if a SQLException occurs.
   */
  public void commit() {
    if (counterThreads.get() != null && counterThreads.get() == 1) {
      counterThreads.remove();
      Connection connection = connectionThread.get();
      try {
        connection.commit();
        connection.setAutoCommit(true);
      } catch (SQLException e) {
        e.printStackTrace();
        throw new FatalException(e);
      } finally {
        connectionThread.remove();
        try {
          connection.close();
        } catch (SQLException ex) {
          throw new FatalException(ex);
        }
      }
    } else {
      counterThreads.set(counterThreads.get()-1);
    }
  }

  /**
   * Rolls back the current transaction.
   * If no transaction is active, it closes the connection if it exists.
   *
   * @throws FatalException if a SQLException occurs.
   */
  public void rollBack() {
    if (counterThreads.get() != null && counterThreads.get() == 1) {
      counterThreads.remove();
      Connection connection = connectionThread.get();
      try {
        connection.rollback();
        connection.setAutoCommit(true);
      } catch (SQLException e) {
        e.printStackTrace();
        throw new FatalException(e);
      } finally {
        connectionThread.remove();
        try {
          connection.close();
        } catch (SQLException ex) {
          throw new FatalException(ex);
        }
      }
    } else {
      counterThreads.set(counterThreads.get() - 1);
    }
  }
}
