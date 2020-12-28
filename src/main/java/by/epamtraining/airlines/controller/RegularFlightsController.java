package by.epamtraining.airlines.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegularFlightsController {

    @GetMapping(value = "/regularflights")
    public String getRegularFlights(Model model){
        return "regularflights";
    }
}
