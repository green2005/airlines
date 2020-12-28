package by.epamtraining.airlines.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PersonnelController {

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DISPATCHER') or hasRole('ROLE_USER')")
    @GetMapping(value = "/personnel")
    public String getPersonnel(Model model) {
        return "personnel";
    }

}
