package ru.hh.school.adaptation.misc;

import ru.hh.school.adaptation.entities.PersonalInfo;

import java.io.UnsupportedEncodingException;

public class CommonUtils {

  public static String makeFioFromPersonalInfo(PersonalInfo personalInfo) {
    StringBuilder sb = new StringBuilder();
    sb.append(personalInfo.getFirstName());
    if (personalInfo.getMiddleName() != null) {
      sb.append(" ").append(personalInfo.getMiddleName());
    }
    sb.append(" ").append(personalInfo.getLastName());
    return sb.toString();
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
