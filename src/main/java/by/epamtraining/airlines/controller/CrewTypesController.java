package by.epamtraining.airlines.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CrewTypesController {

    @GetMapping(value = "/crewTypes")
    public String getCrewTypes(Model mode) {
        return "crewtypes";
    }
}
