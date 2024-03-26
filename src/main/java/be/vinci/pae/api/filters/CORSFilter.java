package be.vinci.pae.api.filters;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * This class implements a JAX-RS filter that enables Cross-Origin Resource Sharing (CORS) for the
 * API.
 */
@Provider
public class CORSFilter implements ContainerResponseFilter {

  /**
   * This method is called by the JAX-RS container after a response is generated. It adds the
   * necessary headers to enable CORS.
   *
   * @param requestContext the incoming request
   * @param responseContext the outgoing response
   * @throws IOException if an I/O error occurs
   */
  @Override
  public void filter(
      ContainerRequestContext requestContext, ContainerResponseContext responseContext)
      throws IOException {
    responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
    responseContext
        .getHeaders()
        .add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
    responseContext.getHeaders().add("Access-Control-Allow-Headers", "*");
    responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
  }
}