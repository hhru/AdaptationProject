package ru.hh.school.adaptation.misc;

import ru.hh.school.adaptation.entities.PersonalInfo;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CommonUtils {

  public static String makeFioFromPersonalInfo(PersonalInfo personalInfo) {
    return Arrays.asList(personalInfo.getFirstName(), personalInfo.getMiddleName(), personalInfo.getLastName())
      .stream()
      .filter(s -> s != null)
      .collect(Collectors.joining(" "));
  }

  public static LocalDateTime dateConverter(Date normalDate) {
    return LocalDateTime.of(normalDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalTime.of(13, 0));
  }

  public static String getContentDispositionFilename(String agent, String fileName) {
    try {
      boolean isInternetExplorer = (agent.toLowerCase().contains("msie"));
      byte[] fileNameBytes = fileName.getBytes((isInternetExplorer) ? ("windows-1251") : ("utf-8"));
      StringBuilder dispositionFileName = new StringBuilder();
      for (byte b : fileNameBytes) {
        dispositionFileName.append((char) (b & 0xff));
      }
      return dispositionFileName.toString();
    } catch (UnsupportedEncodingException ex) {
      return fileName;
    }
  }

}
