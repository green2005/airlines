package by.epamtraining.airlines.dto;

import by.epamtraining.airlines.domain.User;
import by.epamtraining.airlines.domain.UserRole;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;

public class UserRegistrationDTO {
    private String name;
    private String email;
    private String password;


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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
