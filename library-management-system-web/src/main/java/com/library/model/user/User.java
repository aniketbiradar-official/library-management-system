package com.library.model.user;

import java.sql.Timestamp;

public class User {

    private int id;
    private String username;
    private String password;
    private String role;
    private Timestamp createdAt;

    // ===== GETTERS & SETTERS =====

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // ✅ REQUIRED (fixes all errors)
    public String getPassword() {
        return password;
    }

    // ✅ REQUIRED (fixes all errors)
    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
