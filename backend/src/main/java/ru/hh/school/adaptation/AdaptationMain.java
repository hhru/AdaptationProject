package ru.hh.school.adaptation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.hh.nab.core.Launcher;

public class AdaptationMain extends Launcher {

  public static void main(String[] args) {
    doMain(new AnnotationConfigApplicationContext(ProdConfig.class));
  }
}
