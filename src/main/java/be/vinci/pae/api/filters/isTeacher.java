package be.vinci.pae.api.filters;

import jakarta.ws.rs.NameBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Marks elements relevant to teacher roles.
 * This annotation is applied to classes, methods, or other program
 *     elements specifically related to teachers.
 */
@NameBinding
@Retention(RetentionPolicy.RUNTIME)
public @interface isTeacher {

}
