package be.vinci.pae.dal;

import be.vinci.pae.utils.Config;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DALServicesImpl implements DALBackServices, DALServices {
/*
<<<<<<< HEAD

  private Connection connection;

  public DALServicesImpl() {
    try {
      connection = DriverManager.getConnection(Config.getProperty("DatabaseFilePath"), Config.getProperty("DatabaseUser"), Config.getProperty("DatabasePassword"));
    } catch (SQLException e){
      throw new RuntimeException("Unable to connect to database" + e.getMessage());
    }
  }
=======*/
  private ThreadLocal<Connection> connections;
  private BasicDataSource connectionPool;

  public DALServicesImpl() {
//>>>>>>> e72932560d3b7411c173a0c523f7189bc60001a6

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

  public PreparedStatement getPreparedStatement(String sql) {
    try {
      return this.connections.get().prepareStatement(sql);
    } catch (SQLException e) {
      throw new RuntimeException("Unable to connect to database" + e.getMessage());
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
