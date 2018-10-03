package ru.hh.school.adaptation;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.ApplicationContext;
import ru.hh.nab.core.servlet.DefaultServletConfig;

import ru.hh.school.adaptation.filters.AuthFilter;

public class AdaptationServletConfig extends DefaultServletConfig {

  @Override
  public ResourceConfig createResourceConfig(ApplicationContext context) {
    ResourceConfig resourceConfig = super.createResourceConfig(context);
    resourceConfig.register(AuthFilter.class);
    resourceConfig.register(MultiPartFeature.class);
    return resourceConfig;
  }
}
