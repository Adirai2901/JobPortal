package com.jobportal.dto;

import com.jobportal.model.Role;

public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private Role role;

    // ✅ Manually added getters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    // ✅ Optional setters (only needed if you plan to set values manually)
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
