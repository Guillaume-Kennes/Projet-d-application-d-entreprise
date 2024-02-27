package be.vinci.pae.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DALServicesImpl implements DALServices {

  public DALServicesImpl() {
  }

  @Override
  /**
   *
   * @param sql the query
   * @return stmt a prepared statement
   */
  public PreparedStatement getPreparedStatement(String sql) {
    PreparedStatement stmt = null;
    String url = "jdbc:postgresql://coursinfo.vinci.be:5432/dbkawtar_dahman?user=kawtar_dahman";
    Connection connection = null;
    try{
      connection = DriverManager.getConnection(url, "kawtar_dahman", "Groupe06");
    } catch (SQLException e){
      e.printStackTrace();
    }


    try {
      stmt = connection.prepareStatement(sql);
      return stmt;
    } catch (SQLException e){
      e.printStackTrace();
      System.exit(1);
    }
    return stmt;
  }
}
