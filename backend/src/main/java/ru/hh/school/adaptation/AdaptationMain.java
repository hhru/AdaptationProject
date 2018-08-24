package ru.hh.school.adaptation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.hh.nab.core.Launcher;
import ru.hh.school.adaptation.services.ScheduledMailService;

public class AdaptationMain extends Launcher {

  public static void main(String[] args) {
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ProdConfig.class);
    doMain(context, new AdaptationServletConfig());

    context.getBean(ScheduledMailService.class).scheduleAllMailFromDb();
  }
}
