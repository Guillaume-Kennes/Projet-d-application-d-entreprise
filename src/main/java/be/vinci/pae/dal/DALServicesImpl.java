package be.vinci.pae.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DALServicesImpl implements DALServices {

  String url = "jdbc:postgresql://coursinfo.vinci.be:5432/dbkawtar_dahman?user=kawtar_dahman";
  private Connection connection;

  public DALServicesImpl() {
    try {
      connection = DriverManager.getConnection(url, "kawtar_dahman", "Groupe06");
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
