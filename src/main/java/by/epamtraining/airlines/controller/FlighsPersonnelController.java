package by.epamtraining.airlines.controller;

import by.epamtraining.airlines.domain.Flights;
import by.epamtraining.airlines.domain.Personnel;
import by.epamtraining.airlines.dto.FlightsPersonnelDTO;
import by.epamtraining.airlines.exceptions.DomainNotFoundException;
import by.epamtraining.airlines.service.FlightsPersonnelService;
import by.epamtraining.airlines.service.FlightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class FlighsPersonnelController {

    @Autowired
    FlightsPersonnelService flightsPersonnelService;

    @Autowired
    FlightsService flightsService;

    @GetMapping(value = "/flightspersonnel")
    public String getCrewTypes(Model model) {
        List<FlightsPersonnelDTO> personnel = flightsPersonnelService.getFlightsDTO();
        model.addAttribute("flightspersonnel", personnel);
        return "flightspersonnel";
    }

    @PostMapping(value = "/flightspersonnel/delete/{id}")
    public String clearPersonnel(@PathVariable Integer id) {
        flightsPersonnelService.clearPersonnelFromflight(id);
        return "redirect:/flightspersonnel";
    }

    @GetMapping(value = "/flightspersonnel/edit/{id}")
    public String editPersonnelForFlight(@PathVariable Integer id, Model model) {
        Flights flight = flightsService.getById(id).orElseThrow(() -> new DomainNotFoundException());
        if ("clipped".equalsIgnoreCase(flight.getCrewType().getName())) {

            return "flightpersonneleditclipped";
        } else {

            return "flightpersonneledfull";
        }
    }

    @PostMapping(value = "/flightspersonnel/edit")
    public String postPersonnelforFlight(){
        //

    }

}
