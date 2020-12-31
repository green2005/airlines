package by.epamtraining.airlines.controller;

import by.epamtraining.airlines.domain.Airport;
import by.epamtraining.airlines.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class AirportController {
    @Autowired
    private AirportService airportService;

    @GetMapping(value = {"/airports", "/airports/{n}"})
    public String getAirports(@PathVariable(required = false, name = "n") Integer n, Model model) {
        if (n == null) {
            n = 0;
        }
        List<Airport> airports = airportService.getAirports(n, 10);
        model.addAttribute("airports", airports);
        long qty = airportService.getAirportsCount();
        model.addAttribute("airportcount", qty);
        return "airportList";
    }

}
