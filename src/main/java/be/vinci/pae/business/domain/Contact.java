package be.vinci.pae.business.domain;

public interface Contact extends ContactDTO{
  boolean userRoleIsStudent(int id_user);

  boolean correctSchoolYear();
}
