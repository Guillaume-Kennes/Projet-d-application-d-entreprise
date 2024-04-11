package be.vinci.pae.api.filters;

import jakarta.ws.rs.NameBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation for binding authorization checks to JAX-RS resources or methods. This annotation can
 * be applied to JAX-RS resource classes or methods to indicate that authorization checks should be
 * performed.
 */
@NameBinding
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorize {

  /**
   * Returns the array of values associated with this annotation attribute.
   *
   * @return The array of values associated with this annotation attribute.
   */
  String[] value() default {};

}

