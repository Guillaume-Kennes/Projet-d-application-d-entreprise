package be.vinci.pae.utils;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class for loading and accessing configuration properties.
 * This class provides methods to load properties from a file and retrieve them as strings, integers, or booleans.
 */
public class Config {

  static {
    Config.load("dev.properties");
  }

  private static Properties props;

  /**
   * Loads properties from a specified file.
   * @param file The name of the file containing the properties.
   * @throws WebApplicationException if an error occurs while loading the properties.
   */
  public static void load(String file) {
    props = new Properties();
    try (InputStream input = new FileInputStream(file)) {
      props.load(input);
    } catch (IOException e) {
      throw new WebApplicationException(
          Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).type("text/plain")
              .build());
    }
  }

  /**
   * Retrieves a string property by its key.
   * @param key The key of the property to retrieve.
   * @return The value of the property as a string.
   */
  public static String getProperty(String key) {
    return props.getProperty(key);
  }

  /**
   * Retrieves an integer property by its key.
   * @param key The key of the property to retrieve.
   * @return The value of the property as an integer.
   */
  public static Integer getIntProperty(String key) {
    return Integer.parseInt(props.getProperty(key));
  }

  /**
   * Retrieves a boolean property by its key.
   * @param key The key of the property to retrieve.
   * @return The value of the property as a boolean.
   */
  public static boolean getBoolProperty(String key) {
    return Boolean.parseBoolean(props.getProperty(key));
  }

}