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
  private final ThreadLocal<Connection> connectionThread;
  private final ThreadLocal<Integer> counterThreads;
  private final BasicDataSource connectionBDS;

  public DALServicesImpl(){
    connectionThread = new ThreadLocal<>();
    counterThreads = new ThreadLocal<>();
    connectionBDS = new BasicDataSource();

    connectionBDS.setUrl(Config.getProperty("DatabaseFilePath"));
    connectionBDS.setUsername(Config.getProperty("DatabaseUser"));
    connectionBDS.setPassword(Config.getProperty("DatabasePassword"));
    connectionBDS.setDriverClassName("org.postgresql.Driver");
    connectionBDS.setMaxTotal(1);
  }

  public PreparedStatement getPreparedStatement(String sql, boolean primaryKey){
    try{
      return connectionThread.get().prepareStatement(sql, primaryKey ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
    }catch(SQLException e){
      throw new RuntimeException(e.getMessage());
    }
  }

  public PreparedStatement getPreparedStatement(String sql){
    return getPreparedStatement(sql, false);
  }

  public void start(){
    if(counterThreads.get() == null){
      try{
        counterThreads.set(1);
        Connection connection = connectionBDS.getConnection();
        connection.setAutoCommit(false);
        connectionThread.set(connection); //comme ça y'a une connexion pour un thread -> lier un thread à une connexion
      }catch(SQLException e){
        throw new RuntimeException(e.getMessage());
      }
    }else{
      counterThreads.set(counterThreads.get()+1);
    }
  }

  public void commit(){
    if(counterThreads.get() == 1){
      counterThreads.remove(); // ?
      Connection connection = connectionThread.get();
      try{
        connection.setAutoCommit(false);
        connection.commit();
        connectionThread.remove();
        connection.close();
      }catch(SQLException e){
        throw new RuntimeException(e.getMessage());
      }
    } else{
      counterThreads.set(counterThreads.get()-1);
    }
  }

  public void rollBack(){
    Connection connection = connectionThread.get();

    if(counterThreads.get() == null){
      try{
        counterThreads.remove();
        if(connection != null){
          connection.close();
        }
      }catch(SQLException e){
        throw new RuntimeException(e.getMessage());
      }
    }else{
      counterThreads.set(counterThreads.get()-1);
      try{
        connection.rollback();
        connection.setAutoCommit(false);
        counterThreads.remove();
        connection.close();
      }catch(SQLException e){
        throw new RuntimeException(e.getMessage());
      }
    }
  }
}
