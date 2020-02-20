package com.blibli.experience.enums;

public enum UserRole {

  USER("User"),
  MERCHANT("Merchant"),
  ADMIN("Admin");

  private String userRole;

  UserRole(String userRole) {
    this.userRole = userRole;
  }

  public String getUserRole() {
    return userRole;
  }

}
