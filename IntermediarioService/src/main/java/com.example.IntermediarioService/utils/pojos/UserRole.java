package com.example.IntermediarioService.utils.pojos;

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

