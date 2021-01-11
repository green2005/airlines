package by.epamtraining.airlines.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FlighsPersonnelController {

    @GetMapping(value = "/flightspersonnel")
    public String getCrewTypes(Model mode) {
        return "flightspersonnel";
    }
}
