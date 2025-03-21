package com.example.demo.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author MrBird
 */
public class User implements Serializable {
    private static final long serialVersionUID = -2731598327208972274L;

    @NotBlank(message = "{required}")
    private String name;

    @Email(message = "{invalid}")
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
