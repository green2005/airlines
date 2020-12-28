package by.epamtraining.airlines.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfessionsController {

    @GetMapping(value = "/professions")
    public String getProfessions(Model model) {
        return "professions";
    }

}
