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
    } catch (SQLException e) {
      System.out.println("Unable to connect to database" + e.getMessage());
    }
  }

  public PreparedStatement getPreparedStatement(String sql) {
    try {
      return this.connection.prepareStatement(sql);
    } catch (SQLException e) {
      System.out.println("Error while preparing the statement" + e.getMessage());
      return null;
    }
  }
}
