package com.spring.security.services.models.dtos;

public class LoginDTO {
    //Variables
    private String email;
    private String password;

    //Generamos los getters & setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
