package be.vinci.pae.utils;

import be.vinci.pae.business.domain.DomainFactory;
import be.vinci.pae.business.domain.DomainFactoryImpl;
import be.vinci.pae.business.ucc.UserUCC;
import be.vinci.pae.business.ucc.UserUCCImpl;
import be.vinci.pae.dal.UserDAO;
import be.vinci.pae.dal.UserDAOImpl;
import jakarta.ws.rs.ext.Provider;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.mockito.Mockito;

@Provider
public class AppBinderTest extends AbstractBinder {
  @Override
  protected void configure() {
    bind(DomainFactoryImpl.class).to(DomainFactory.class);
    bind(UserUCCImpl.class).to(UserUCC.class);
    bind(Mockito.mock(UserDAOImpl.class)).to(UserDAO.class);
  }
}
