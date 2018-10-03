package ru.hh.school.adaptation.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.inject.Singleton;
import org.apache.poi.util.IOUtils;

@Singleton
public class FileUploadService {
  private String uploadDir;

  public FileUploadService() {
    uploadDir = System.getProperty("uploadDir");
  }

  public String saveFile(Integer employeeId, InputStream file, String filename) {
    String dirname = uploadDir + "/" + employeeId;
    File directory = new File(dirname);
    if (!directory.exists()){
      directory.mkdir();
    }

    try {
      Files.write(Paths.get(dirname + "/" + filename), IOUtils.toByteArray(file));
    } catch (IOException e) {
      throw new RuntimeException(String.format("Error upload file, for employee %s", employeeId));
    }
    return filename;
  }

  public List<String> getFileList(Integer employeeId) {
    String[] files = new File(uploadDir + "/" + employeeId).list();
    if (files == null) {
      return Collections.emptyList();
    }
    return Arrays.asList(files);
  }

  public void deleteFile(Integer employeeId, String filename) {
    String fullName = uploadDir + "/" + employeeId + "/" + filename;
    File file = new File(fullName);

    if(!file.delete()) {
      throw new RuntimeException(String.format("Something went wrong while deleting file %s", fullName));
    }
  }

  public byte[] getFile(Integer employeeId, String filename) {
    byte[] data;
    try {
      data = Files.readAllBytes(Paths.get(uploadDir + "/" + employeeId + "/" + filename));
    } catch (IOException e) {
      throw new RuntimeException(String.format("Error download file, for employee %s", employeeId));
    }
    return data;
  }

}
