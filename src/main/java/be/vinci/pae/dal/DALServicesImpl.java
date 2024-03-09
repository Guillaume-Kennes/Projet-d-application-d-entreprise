package be.vinci.pae.dal;

import be.vinci.pae.utils.Config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DALServicesImpl implements DALServices {


  private Connection connection;

  public DALServicesImpl() {
    try {
      connection = DriverManager.getConnection(Config.getProperty("DatabaseFilePath"), Config.getProperty("DatabaseUser"), Config.getProperty("DatabasePassword"));
    } catch (SQLException e){
      throw new RuntimeException("Unable to connect to database" + e.getMessage());
    }
  }

  public Connection getConnection() { return this.connection;}

  public PreparedStatement getPreparedStatement(String sql) {
    try {
      return this.connection.prepareStatement(sql);
    } catch (SQLException e) {
      throw new RuntimeException("Unable to connect to database" + e.getMessage());
    }
  }
}
