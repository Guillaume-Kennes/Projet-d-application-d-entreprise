package be.vinci.pae.dal;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.commons.dbcp2.BasicDataSource;

public class DALServicesImpl implements DALBackServices, DALServices {

  private ThreadLocal<Connection> connections;
  private BasicDataSource connectionPool;

  /**
   * Constructs a new instance of DALServicesImpl.
   * Initializes the database connection pool
   * and loads database properties from a properties file.
   */
  public DALServicesImpl() {

    Properties properties = new Properties();
    try (InputStream input = new FileInputStream("dev.properties")) {
      properties.load(input);
    } catch (IOException e) {
      e.printStackTrace();
    }

    connections = new ThreadLocal<>();
    connectionPool = new BasicDataSource();
    connectionPool.setUrl(properties.getProperty("DatabaseFilePath"));
    connectionPool.setUsername(properties.getProperty("DatabaseUser"));
    connectionPool.setPassword(properties.getProperty("JWATSecret"));

    connections.set(start());
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
      return this.connections.get().prepareStatement(sql);
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
    try {
      return connectionPool.getConnection();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Commits a transaction and closes the connection.
   *
   * @param connection The database connection.
   */

  @Override
  public void commit(Connection connection) {
    try {
      connection.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Rolls back a transaction and closes the connection.
   *
   * @param connection The database connection.
   */

  @Override
  public void rollBack(Connection connection) {
    try {
      connection.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
