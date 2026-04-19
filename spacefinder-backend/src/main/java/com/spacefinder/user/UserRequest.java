package com.spacefinder.user;

import lombok.Data;

@Data
public class UserRequest {
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String password;
    private String role;
    private String status;
}
