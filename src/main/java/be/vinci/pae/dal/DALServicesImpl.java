package be.vinci.pae.dal;

import be.vinci.pae.utils.Config;
import be.vinci.pae.utils.exception.UnauthorizedException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 * Implementation of the DALServices interface.
 * Manages database connections and provides methods for database operations.
 */
public class DALServicesImpl implements DALBackServices, DALServices {

  private ThreadLocal<Connection> connections;
  private BasicDataSource connectionPool;

  /**
   * Constructs a new instance of DALServicesImpl. Initializes the database connection pool and
   * loads database properties from a properties file.
   */
  public DALServicesImpl() {

//    Properties properties = new Properties();
//    try (InputStream input = new FileInputStream("dev.properties")) {
//      properties.load(input);
//    } catch (IOException e) {
//      e.printStackTrace();
//    }

    connections = new ThreadLocal<>();
    connectionPool = new BasicDataSource();
    connectionPool.setDriverClassName("org.postgresql.Driver");
    connectionPool.setUrl(Config.getProperty("DatabaseFilePath"));
    connectionPool.setUsername(Config.getProperty("DatabaseUser"));
    connectionPool.setPassword(Config.getProperty("JWTSecret"));
    connectionPool.setMaxTotal(5);
  }

  /**
   * Retrieves a prepared statement for the given SQL query.
   *
   * @param sql The SQL query.
   * @return A prepared statement.
   * @throws RuntimeException If unable to connect to the database.
   */
  public PreparedStatement getPreparedStatement(String sql) {
    try {
      Connection connection = connections.get();

      if (connection == null) {
        throw new UnauthorizedException("No connection to the database");
      }
      return connection.prepareStatement(sql);

    } catch (SQLException e) {
      throw new RuntimeException("Unable to connect to database" + e.getMessage());
    }
  }


  /**
   * Establishes a database connection.
   *
   * @return A database connection.
   * @throws RuntimeException If connection fails.
   */
  @Override
  public Connection start() {
    if (connections.get() == null) {

      try {
        connections.set(connectionPool.getConnection());
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }

      try {
        connections.get().setAutoCommit(false);
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    } else {
      throw new RuntimeException("Already a connection");
    }

    return connections.get();
  }

  /**
   * Commits a transaction and closes the connection.
   */
  @Override
  public void commit() {
    try {
      connections.get().commit();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      try {
        connections.get().close();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      } finally {
        connections.remove();
      }
    }
  }

  /**
   * Rolls back a transaction and closes the connection.
   */
  @Override
  public void rollBack() {
    try {
      connections.get().rollback();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      try {
        connections.get().close();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      } finally {
        connections.remove();
      }
    }
  }


}
