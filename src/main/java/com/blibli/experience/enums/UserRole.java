package com.blibli.experience.enums;

public enum UserRole {

  ROLE_USER("ROLE_USER"),
  ROLE_MERCHANT("ROLE_MERCHANT"),
  ROLE_ADMIN("ROLE_ADMIN");

  private String userRole;

  UserRole(String userRole) {
    this.userRole = userRole;
  }

  public String getUserRole() {
    return userRole;
  }

}
