package be.vinci.pae.main.utils;

import be.vinci.pae.main.domain.DomainFactory;
import be.vinci.pae.main.domain.DomainFactoryImpl;
import be.vinci.pae.main.services.UserDataService;
import be.vinci.pae.main.services.UserDataServiceImpl;
import jakarta.inject.Singleton;
import jakarta.ws.rs.ext.Provider;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

@Provider
public class ApplicationBinder extends AbstractBinder {

  @Override
  protected void configure() {
    bind(DomainFactoryImpl.class).to(DomainFactory.class).in(Singleton.class);
    bind(UserDataServiceImpl.class).to(UserDataService.class).in(Singleton.class);
  }
}
