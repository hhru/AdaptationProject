package ru.hh.school.adaptation.resources;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.hh.school.adaptation.dto.CommentDto;
import ru.hh.school.adaptation.dto.CommentCreateDto;
import ru.hh.school.adaptation.dto.EmployeeBriefDto;
import ru.hh.school.adaptation.dto.EmployeeCreateDto;
import ru.hh.school.adaptation.dto.EmployeeDto;
import ru.hh.school.adaptation.dto.EmployeeUpdateDto;
import ru.hh.school.adaptation.dto.TransitionDto;
import ru.hh.school.adaptation.misc.CommonUtils;
import ru.hh.school.adaptation.misc.Named;
import ru.hh.school.adaptation.dto.WorkflowStepDto;
import ru.hh.school.adaptation.dto.EmployeeCreateInternalDto;
import ru.hh.school.adaptation.entities.User;
import ru.hh.school.adaptation.exceptions.AccessDeniedException;
import ru.hh.school.adaptation.services.CommentService;
import ru.hh.school.adaptation.services.EmployeeService;
import ru.hh.school.adaptation.services.TransitionService;
import ru.hh.school.adaptation.services.auth.AuthService;

import javax.inject.Singleton;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/")
@Singleton
public class EmployeeResource {
  private final EmployeeService employeeService;
  private final TransitionService transitionService;
  private final CommentService commentService;
  private final AuthService authService;

  public EmployeeResource(EmployeeService employeeService, TransitionService transitionService,
                          CommentService commentService, AuthService authService) {
    this.employeeService = employeeService;
    this.transitionService = transitionService;
    this.commentService = commentService;
    this.authService = authService;
  }

  @GET
  @Produces("application/json")
  @Path("/employee/{id}/step")
  @ResponseBody
  public TransitionDto getCurrentEmployeeTransition(@PathParam("id") Integer id) {
    return transitionService.getCurrentTransitionByEmployeeId(id);
  }

  @PUT
  @Produces("application/json")
  @Path("/employee/{id}/step/next")
  @ResponseBody
  public WorkflowStepDto setEmployeeTransition(@PathParam("id") Integer id) {
    return transitionService.setEmployeeNextTransition(employeeService.getEmployee(id));
  }

  @GET
  @Produces("application/json")
  @Path("/employee/{id}/step/all")
  @ResponseBody
  public List<TransitionDto> getAllTransition(@PathParam("id") Integer id) {
    return transitionService.getAllTransitionDtoByEmployeeId(id);
  }

  @GET
  @Produces("application/json")
  @Path("/employee/all")
  @ResponseBody
  public List<EmployeeBriefDto> getAll() {
    return employeeService.getBriefEmployeesList();
  }

  @GET
  @Produces("application/json")
  @Path("/employee/{id}")
  @ResponseBody
  public EmployeeDto getEmployee(@PathParam("id") Integer id) {
    return employeeService.getEmployeeDto(id);
  }

  @POST
  @Produces("application/json")
  @Path("/employee/{id}/dismiss")
  @ResponseBody
  public void dismissEmployee(@PathParam("id") Integer id, @RequestBody String dismissComment) {
    employeeService.dismissEmployee(id, dismissComment);
  }

  @POST
  @Produces("application/json")
  @Path("/employee/create")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public EmployeeDto createEmployee(@RequestBody EmployeeCreateDto employeeCreateDto) {
    User user = authService.getUser().orElseThrow(() -> new AccessDeniedException("The user is not logged in."));
    EmployeeCreateInternalDto employeeCreateInternalDto = new EmployeeCreateInternalDto(user.getId(), employeeCreateDto);
    return employeeService.createEmployee(employeeCreateInternalDto);
  }

  @PUT
  @Produces("application/json")
  @Path("/employee/update")
  @ResponseBody
  public EmployeeDto updateEmployee(@RequestBody EmployeeUpdateDto employeeUpdateDto){
    return employeeService.updateEmployee(employeeUpdateDto);
  }

  @POST
  @Produces("application/json")
  @Path("/comment/create")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public CommentDto createComment(@RequestBody CommentCreateDto commentCreateDto){
    return commentService.createCommentFromDto(commentCreateDto);
  }

  @DELETE
  @Produces("application/json")
  @Path("/comment/remove/{id}")
  @ResponseBody
  public void removeComment(@PathParam("id") Integer commentId) {
    commentService.removeComment(commentId);
  }

  @GET
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  @Path("/employee/{employeeId}/probation_result")
  @ResponseBody
  public Response getEmployeeProbationResultDoc(@PathParam("employeeId") Integer employeeId, @HeaderParam("user-agent") String userAgent) {
    Named<byte[]> document = employeeService.generateProbationResultDoc(employeeId);
    return Response.ok(document.get()).header(
        "Content-Disposition", String.format(
            "attachment; filename=\"%s.docx\"",
            CommonUtils.getContentDispositionFilename(userAgent, document.name())
        )
    ).build();
  }

}
