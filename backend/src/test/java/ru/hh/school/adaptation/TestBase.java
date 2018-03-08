package ru.hh.school.adaptation;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import ru.hh.school.adaptation.resources.ExampleResource;

import javax.inject.Inject;

@ContextConfiguration(classes = {TestConfig.class})
public abstract class TestBase extends AbstractJUnit4SpringContextTests {

  @Inject
  ExampleResource resource;

  protected ExampleResource getExampleResource() {
    return resource;
  }
}
