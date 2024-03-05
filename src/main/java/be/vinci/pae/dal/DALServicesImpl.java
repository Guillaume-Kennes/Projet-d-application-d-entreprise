package be.vinci.pae.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DALServicesImpl implements DALBackServices, DALServices {

  private ThreadLocal<Connection> connections;
  private BasicDataSource connectionPool;

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
    connectionPool.setUsername("DatabaseUser");
    connectionPool.setPassword("JWATSecret");

    connections.set(start());
  }

  public PreparedStatement getPreparedStatement(String sql) {
    try {
      return this.connections.get().prepareStatement(sql);
    } catch (SQLException e) {
      System.out.println("Error while preparing the statement" + e.getMessage());
      return null;
    }
  }

  @Override
  public Connection start() {
    try {
      return connectionPool.getConnection();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void commit(Connection connection) {
    try {
      connection.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void rollBack(Connection connection) {
    try {
      connection.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
