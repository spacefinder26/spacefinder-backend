package com.spacefinder.user;

import lombok.Data;

@Data
public class UserDTO {
    private String name;
    private String email;
    private String password;
    private Role role;
    private String status;
}
