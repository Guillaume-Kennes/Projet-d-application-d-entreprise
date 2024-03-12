package be.vinci.pae.dal;

import java.sql.Connection;

public interface DALServices {

  Connection start();

  void commit(Connection connection);

  void rollBack(Connection connection);

}
