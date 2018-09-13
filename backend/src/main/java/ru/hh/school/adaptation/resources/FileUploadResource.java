package ru.hh.school.adaptation.resources;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.hh.school.adaptation.misc.CommonUtils;
import ru.hh.school.adaptation.services.FileUploadService;

@Path("/")
@Singleton
public class FileUploadResource {
  private final FileUploadService fileUploadService;

  @Inject
  public FileUploadResource(FileUploadService fileUploadService){
    this.fileUploadService = fileUploadService;
  }

  @POST
  @Path("/employee/{employeeId}/upload")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  public String saveFile(@PathParam("employeeId") Integer employeeId,
                       @FormDataParam("file") InputStream file,
                       @FormDataParam("file") FormDataContentDisposition fileContentDisposition) {
    if (employeeId == null || file == null || fileContentDisposition.getFileName() == null) {
      throw new RuntimeException(String.format("Error upload file, for employee %s", employeeId));
    }
    String filename = new String(fileContentDisposition.getFileName().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    return fileUploadService.saveFile(employeeId, file, filename);
  }

  @POST
  @Path("/employee/{employeeId}/remove")
  public void deleteFile(@PathParam("employeeId") Integer employeeId, @RequestBody String filename) {
    if (employeeId == null || filename == null) {
      throw new RuntimeException(String.format("Error delete file, for employee %s", employeeId));
    }
    fileUploadService.deleteFile(employeeId, filename);
  }

  @GET
  @Path("/employee/{employeeId}/file_list")
  @Produces(MediaType.APPLICATION_JSON)
  public List<String> fileList(@PathParam("employeeId") Integer employeeId) {
    return fileUploadService.getFileList(employeeId);
  }

  @GET
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  @Path("/employee/{employeeId}/download/{filename}")
  @ResponseBody
  public Response downloadFile(@PathParam("employeeId") Integer employeeId,
                               @PathParam("filename") String filename,
                               @HeaderParam("user-agent") String userAgent) {
    if (employeeId == null || filename == null) {
      throw new RuntimeException(String.format("Error download file, for employee %s", employeeId));
    }

    return Response.ok(fileUploadService.getFile(employeeId, filename)).header(
        "Content-Disposition", String.format(
            "attachment; filename=\"%s\"",
            CommonUtils.getContentDispositionFilename(userAgent, filename)
        )
    ).build();
  }

}
