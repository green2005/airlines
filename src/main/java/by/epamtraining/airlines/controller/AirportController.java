package by.epamtraining.airlines.controller;

import by.epamtraining.airlines.NavPageLink;
import by.epamtraining.airlines.domain.Airport;
import by.epamtraining.airlines.exceptions.DomainNotFoundException;
import by.epamtraining.airlines.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AirportController {
    @Autowired
    private AirportService airportService;

    @GetMapping(value = {"/airports", "/airports/{n}"})
    public String getAirports(@PathVariable(required = false, name = "n") Integer n, Model model) {
        long recordCount = airportService.getAirportsCount();
        n = NavPageLink.getCurrentPageNo(n, recordCount);
        return getPage(n, recordCount, model);
    }

    @GetMapping(value = {"/airports/prev/{n}"})
    public String getPrevPageAirports(@PathVariable(required = false, name = "n") Integer n, Model model) {
        long recordCount = airportService.getAirportsCount();
        if (n > 0) {
            n--;
        }
        n = NavPageLink.getCurrentPageNo(n, recordCount);
        return getPage(n, recordCount, model);
    }

    @GetMapping(value = {"/airports/next/{n}"})
    public String getNextPageAirports(@PathVariable(required = false, name = "n") Integer n, Model model) {
        long recordCount = airportService.getAirportsCount();
        n++;
        n = NavPageLink.getCurrentPageNo(n, recordCount);
        return getPage(n, recordCount, model);
    }

    @GetMapping(value = {"/airports/edit/{pageno}/{id}"})
    public String getAirportsEdit(@PathVariable Integer pageno, @PathVariable Integer id, Model model) {
        Airport airport = airportService.getById(id).orElseThrow(DomainNotFoundException::new);
        model.addAttribute("pageno", pageno);
        model.addAttribute("airport", airport);
        return "airportedit";
    }

    @PostMapping(value = {"/airports/edit/{pageno}/{id}"})
    public String postAirportsEdit(@PathVariable Integer pageno, @PathVariable Integer id, Airport airport) {
        airportService.save(airport);
        return "redirect:/airports/".concat(Integer.toString(pageno));
    }

    private String getPage(Integer pageNo, long recordCount, Model model) {
        List<Airport> airports = airportService.getAirports(pageNo, NavPageLink.RECORDS_PER_PAGE);
        model.addAttribute("airports", airports);
        List<NavPageLink> pageLinks = NavPageLink.getPageLinks(recordCount, pageNo, "/airports");
        model.addAttribute("navpages", pageLinks);
        model.addAttribute("airportcount", recordCount);
        model.addAttribute("pageno", pageNo);
        return "airportList";
    }

    @PostMapping(value = "/airports/delete/{pageno}/{id}")
    public String deleteAirport(@PathVariable Integer pageno, @PathVariable Integer id) {
        airportService.deleteById(id);
        return "redirect:/airports/".concat(Integer.toString(pageno));
    }
}
