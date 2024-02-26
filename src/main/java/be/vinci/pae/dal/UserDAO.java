package be.vinci.pae.dal;

import be.vinci.pae.business.domain.User;
import be.vinci.pae.business.domain.UserDTO;
import java.util.List;

public interface UserDAO {

  UserDTO getUserByEmail(String email);

}