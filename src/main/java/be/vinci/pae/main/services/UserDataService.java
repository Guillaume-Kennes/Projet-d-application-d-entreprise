package be.vinci.pae.main.services;

import be.vinci.pae.main.domain.User;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.List;

public interface UserDataService {

  List<User> getAll();
}
