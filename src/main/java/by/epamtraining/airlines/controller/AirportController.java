package by.epamtraining.airlines.controller;

import by.epamtraining.airlines.NavPageLink;
import by.epamtraining.airlines.domain.Airport;
import by.epamtraining.airlines.exceptions.DomainNotFoundException;
import by.epamtraining.airlines.service.AirportService;
import org.attoparser.util.TextUtil;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.TextUtils;

import java.util.List;

@Controller
public class AirportController {
    private static final int RECORDS_PER_PAGE = 5;


    @Autowired
    private AirportService airportService;

    @GetMapping(value = {"/airports", "/airports/{n}"})
    public String getAirports(@PathVariable(required = false, name = "n") Integer n,
                              @RequestParam(required = false, name = "sortfield", defaultValue = "shortName") String sortfield,
                              @RequestParam(required = false, name = "sortasc", defaultValue = "true") Boolean orderAsc,
                              Model model) {
        if ((n == null) || (n < 1)) {
            n = 1;
        }
        Page<Airport> airportPage = airportService.getAirports(n, RECORDS_PER_PAGE, sortfield, orderAsc);
        int totalPageqty = airportPage.getTotalPages();
        if (n > totalPageqty) {
            n = totalPageqty;
            airportPage = airportService.getAirports(n, RECORDS_PER_PAGE, sortfield, orderAsc);
        }
        model.addAttribute("airports", airportPage.getContent());
        model.addAttribute("pagecount", totalPageqty);
        model.addAttribute("pageno", n);
        model.addAttribute("recordcount", airportPage.getTotalElements());
        model.addAttribute("sortfield", sortfield);
        model.addAttribute("sortasc", orderAsc);
        return "airportlist";
    }

    @PostMapping(value = {"/airports/edit/{pageno}/{id}", "/airports/edit", "/airports/edit/{pageno}"})
    public String getAirportsEdit(@PathVariable(required = false) Integer pageno,
                                  @PathVariable(required = false) Integer id,
                                  @RequestParam(required = false, name = "sortfield", defaultValue = "shortName") String sortfield,
                                  @RequestParam(required = false, name = "sortasc", defaultValue = "true") Boolean orderAsc,
                                  Model model) {
        Airport airport;
        if (pageno == null) {
            pageno = 1;
        }
        if (id == null) {
            airport = new Airport();
        } else {
            airport = airportService.getById(id).orElseThrow(DomainNotFoundException::new);
        }
        model.addAttribute("pageno", pageno);
        model.addAttribute("sortfield", sortfield);
        model.addAttribute("sortasc", orderAsc);
        model.addAttribute("airport", airport);
        return "airportedit";
    }

    @PostMapping(value = {"/airports/save/{pageno}", "/airports/save"})
    public String postAirportsEdit(@PathVariable(required = false) Integer pageno,
                                   @RequestParam(required = false, name = "sortfield", defaultValue = "shortName") String sortfield,
                                   @RequestParam(required = false, name = "sortasc", defaultValue = "true") Boolean orderAsc,
                                   Airport airport) {
        if (pageno == null) {
            pageno = 1;
        }
        airportService.save(airport);
        return "redirect:/airports/".
                concat(Integer.toString(pageno)).
                concat(String.format("/?sortfield=%s&sortasc=%b", sortfield, orderAsc));
    }

    @PostMapping(value = "/airports/delete/{pageno}/{id}")
    public String deleteAirport(@PathVariable(required = false) Integer pageno,
                                @PathVariable Integer id,
                                @RequestParam(required = false, name = "sortfield", defaultValue = "shortName") String sortfield,
                                @RequestParam(required = false, name = "sortasc", defaultValue = "true") Boolean orderAsc
    ) {
        if (pageno == null) {
            pageno = 1;
        }
        airportService.deleteById(id);
        return "redirect:/airports/".
                concat(Integer.toString(pageno)).
                concat(String.format("/?sortfield=%s&sortasc=%b", sortfield, orderAsc));
    }
}