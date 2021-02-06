package org.example.resourceserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  private final Environment env;

  @Autowired
  public UserController(Environment env) {
    this.env = env;
  }

  @GetMapping("/status/check")
  public String status() {
    return "working on port " + env.getProperty("local.server.port");
  }


  @PreAuthorize("hasAnyAuthority('ROLE_admin') or #id == #jwt.subject")
  @DeleteMapping("/{id}")
  public String deleteUser(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
    return "deleted user with id " + id + " JWT subject " + jwt.getSubject();
  }
}
