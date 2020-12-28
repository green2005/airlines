package by.epamtraining.airlines.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FlightsController {

    @GetMapping(value = "/flights")
    public String getFlights() {
        return "flights";
    }
}
