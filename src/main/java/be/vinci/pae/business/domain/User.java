package be.vinci.pae.business.domain;


public interface User extends UserDTO {


  boolean checkPassword(String password);

  String hashPassword(String password);

}

