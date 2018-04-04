package ru.hh.school.adaptation;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;


@ContextConfiguration(classes = {TestConfig.class})
public abstract class TestBase extends AbstractJUnit4SpringContextTests {
}
