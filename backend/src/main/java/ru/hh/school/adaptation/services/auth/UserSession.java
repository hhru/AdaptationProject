package ru.hh.school.adaptation.services.auth;

import javax.servlet.http.HttpSession;

public class UserSession {
  HttpSession session;

  public UserSession(HttpSession session) {
    this.session = session;
  }

  public Integer getId() {
    return (Integer) session.getAttribute("id");
  }

  public void setId(Integer id) {
    session.setAttribute("id", id);
  }

  public void logout() {
    session.removeAttribute("id");
  }
}
