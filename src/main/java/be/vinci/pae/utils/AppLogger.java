package be.vinci.pae.utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/** This class provides a simple way to log messages to a file. */
public class AppLogger {

  private static final Logger logger = Logger.getLogger(AppLogger.class.getName());
  private static FileHandler fileHandler;
  private static SimpleFormatter formatter;

  static {
    try {
      // Get the log directory path
      String logDirectoryPath = "logs";
      createDirectory(logDirectoryPath);

      // Create the file handler and formatter
      fileHandler = new FileHandler("logs/log-%g.log", 1024 * 1024, 1000, true);
      formatter = new SimpleFormatter();
      fileHandler.setFormatter(formatter);

      // Add the file handler to the logger
      logger.addHandler(fileHandler);
      logger.setLevel(Level.FINE);
    } catch (IOException | SecurityException e) {
      e.printStackTrace();
    }
  }

  /**
   * Returns a logger instance.
   *
   * @param message The message to log.
   * @return The logger instance.
   */
  public static Logger getLogger(String message) {
    return logger;
  }

  /**
   * Creates the log directory if it does not exist.
   *
   * @param directoryPath The path of the directory.
   */
  private static void createDirectory(String directoryPath) {
    try {
      File logDirectory = new File(directoryPath);
      if (!logDirectory.exists()) {
        logDirectory.mkdirs();
      }
    } catch (SecurityException e) {
      e.printStackTrace();
    }
  }
}
