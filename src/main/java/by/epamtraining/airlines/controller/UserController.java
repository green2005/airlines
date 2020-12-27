package by.epamtraining.airlines.controller;

import by.epamtraining.airlines.service.UserService;
import by.epamtraining.airlines.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/users")
    public String getUsers(Model model) {
        return "users";
    }


}
