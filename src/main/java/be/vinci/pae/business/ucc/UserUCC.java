package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.domain.UserDTO;
import java.util.List;

public interface UserUCC {

 UserDTO login(String email, String password);

}
