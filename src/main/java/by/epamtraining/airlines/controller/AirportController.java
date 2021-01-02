package by.epamtraining.airlines.controller;

import by.epamtraining.airlines.NavPageLink;
import by.epamtraining.airlines.domain.Airport;
import by.epamtraining.airlines.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@Controller
public class AirportController {
    @Autowired
    private AirportService airportService;

    @GetMapping(value = {"/airports", "/airports/{n}"})
    public String getAirports(@PathVariable(required = false, name = "n") Integer n, Model model) {
        long recordCount = airportService.getAirportsCount();
        n = NavPageLink.getCurrentPageNo(n, recordCount);
        List<Airport> airports = airportService.getAirports(n, NavPageLink.RECORDS_PER_PAGE);
        model.addAttribute("airports", airports);
        long qty = airportService.getAirportsCount();
        List<NavPageLink> pageLinks = NavPageLink.getPageLinks(qty, n, "/airports");
        model.addAttribute("navpages", pageLinks);
        model.addAttribute("airportcount", qty);
        model.addAttribute("pageno", n);
        return "airportList";
    }

    @PostMapping(value = "/airports/delete/{id}/{pageno}")
    public String deleteAirport(@PathVariable Integer id, @PathVariable Integer pageno) {
        airportService.deleteById(id);
        return "redirect:/airports/".concat(Integer.toString(pageno));
    }

}
