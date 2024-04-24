package be.vinci.pae.main;

import be.vinci.pae.utils.ApplicationBinder;
import be.vinci.pae.utils.Config;
import be.vinci.pae.utils.LoggingFilter;
import be.vinci.pae.utils.WebExceptionMapper;
import java.io.IOException;
import java.net.URI;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Main class.
 */
public class Main {
  /**
   * The base URI for the application.
   * This URI represents the root endpoint of the application.
   * It is used as the base URL for making HTTP requests.
   * Ensure this URI is properly configured in the application's configuration.
   */
  public static final String BASE_URI = Config.getProperty("BaseUri");


  /**
   * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
   *
   * @return Grizzly HTTP server.
   */
  public static HttpServer startServer() {

    final ResourceConfig rc = new ResourceConfig().packages("be.vinci.pae.api")
        .register(ApplicationBinder.class)
        .register(WebExceptionMapper.class)
        .register(LoggingFilter.class);
    return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
  }


  /**
   * Main method to start the Jersey application server.
   *
   * @param args Command-line arguments (not used in this method).
   * @throws IOException if an I/O error occurs while starting the server.
   */
  public static void main(String[] args) throws IOException {

    final HttpServer server = startServer();
    System.out.println(String.format("Jersey app started with endpoints available at "
        + "%s%nHit Ctrl-C to stop it...", BASE_URI));
    System.in.read();
    server.stop();
  }
}


