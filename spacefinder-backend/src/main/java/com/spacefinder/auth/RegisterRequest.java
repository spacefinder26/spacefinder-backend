package com.spacefinder.auth;

import com.spacefinder.user.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private Role role;
    private String status;
}
