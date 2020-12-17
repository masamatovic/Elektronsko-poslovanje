package com.project.online_library.dto;

public class LoginDto {

    private String username;
    private String password;

    public LoginDto() {}

    public String getUsername() {
        return username;
    }

    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
