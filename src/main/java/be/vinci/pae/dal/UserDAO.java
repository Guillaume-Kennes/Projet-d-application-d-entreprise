package be.vinci.pae.dal;

import be.vinci.pae.business.domain.User;
import java.util.List;

public interface UserDAO {

  List<User> getAll();
}
