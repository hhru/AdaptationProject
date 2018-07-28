package ru.hh.school.adaptation.resources;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.hh.school.adaptation.dto.AccessRuleCreateDto;
import ru.hh.school.adaptation.dto.AccessRuleDto;
import ru.hh.school.adaptation.services.AdminService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Path("/")
@Singleton
public class AdminResource {
  private final AdminService adminService;

  @Inject
  public AdminResource(AdminService adminService) {
    this.adminService = adminService;
  }

  @GET
  @Produces("application/json")
  @Path("/rule_list")
  @ResponseBody
  public List<AccessRuleDto> ruleList() {
    return adminService.getRuleList();
  }

  @POST
  @Produces("application/json")
  @Path("/update_access/{hhid}")
  @ResponseBody
  public void updateAccessRule(@PathParam("hhid") Integer hhId, @RequestBody String accessType) {
    adminService.updateAccessRule(hhId, accessType);
  }

  @POST
  @Produces("application/json")
  @Path("/new_rule")
  @ResponseBody
  public Integer updateAccessRule(@RequestBody AccessRuleCreateDto accessDto) {
    return adminService.createAccessRuleByDto(accessDto);
  }

}
