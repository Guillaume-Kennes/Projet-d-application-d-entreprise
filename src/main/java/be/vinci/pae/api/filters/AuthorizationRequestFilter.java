package be.vinci.pae.api.filters;

import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.ucc.UserUCC;
import be.vinci.pae.utils.Config;
import be.vinci.pae.utils.exception.FatalException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Request filter for handling authorization checks. This filter checks for the presence of a JWT
 * token in the request header and verifies its validity.
 */
@Singleton
@Provider
@Authorize
@Priority(1)
public class AuthorizationRequestFilter {

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final JWTVerifier jwtVerifier = JWT.require(this.jwtAlgorithm).withIssuer("auth0")
      .build();
  @Inject
  private UserUCC userUCC;


  /**
   * Filters incoming requests to verify authorization.
   *
   * @param requestContext The request context to filter.
   */
  public void filter(ContainerRequestContext requestContext) {

    String token = requestContext.getHeaderString("Authorization");
    if (token == null) {
      requestContext.abortWith(Response.status(Status.UNAUTHORIZED)
          .entity("A token is needed to access this resource").build());
    } else {
      DecodedJWT decodedToken = null;
      try {
        decodedToken = this.jwtVerifier.verify(token);
      } catch (Exception e) {
        throw new FatalException(e); //à vérifier
      }
      User authenticatedUser = (User) userUCC.getUserById(decodedToken.getClaim("user").asInt()); //pour avoir l'id du user //poser la question au prof
      if (authenticatedUser == null) {
        requestContext.abortWith(Response.status(Status.FORBIDDEN)
            .entity("You are forbidden to access this resource").build());
      }
      requestContext.setProperty("user", authenticatedUser); //user ici comme le STORE_NAME dans auths.js dans le front
    }
  }

}
