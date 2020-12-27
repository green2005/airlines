package by.epamtraining.airlines.controller;

import by.epamtraining.airlines.domain.Airport;
import by.epamtraining.airlines.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AirportController {
    @Autowired
    private AirportService airportService;

    @GetMapping(value = "/airports")
    public String getAirports(Model model) {
        List<Airport> airportList = airportService.getAirports();
        model.addAttribute("airports", airportList);
        return "airportList";
    }

}
