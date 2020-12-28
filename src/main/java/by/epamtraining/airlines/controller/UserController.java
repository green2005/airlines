package by.epamtraining.airlines.controller;

import by.epamtraining.airlines.service.UserService;
import by.epamtraining.airlines.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getUsers(Model model) {
        List<User> users = userService.getUserList();
        model.addAttribute("users", users);
        return "users";
    }

}
