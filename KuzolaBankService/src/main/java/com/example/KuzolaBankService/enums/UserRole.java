package com.example.KuzolaBankService.enums;

public enum UserRole {
  ADMIN("admin"),
  CLIENTE("cliente");

  private String role;

  UserRole(String role) {
    this.role = role;
  }

  public String getValue() {
    return role;
  }
}

