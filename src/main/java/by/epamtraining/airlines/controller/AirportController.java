package by.epamtraining.airlines.controller;

import by.epamtraining.airlines.domain.Airport;
import by.epamtraining.airlines.exceptions.DomainNotFoundException;
import by.epamtraining.airlines.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static by.epamtraining.airlines.AppStarter.RECORDS_PER_PAGE;

@Controller
public class AirportController {

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
        if ((n > totalPageqty) && (totalPageqty > 0)) {
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

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DISPATCHER')")
    @GetMapping(value = {"/airports/edit/{pageno}/{id}", "/airports/edit", "/airports/edit/{pageno}"})
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

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DISPATCHER')")
    @PostMapping(value = {"/airports/edit/{pageno}", "/airports/edit"})
    public String postAirportsEdit(@PathVariable(required = false) Integer pageno,
                                   @RequestParam(required = false, name = "sortfield", defaultValue = "shortName") String sortfield,
                                   @RequestParam(required = false, name = "sortasc", defaultValue = "true") Boolean orderAsc,
                                   @Valid Airport airport) {
        if (pageno == null) {
            pageno = 1;
        }
        airportService.save(airport);
        return "redirect:/airports/".
                concat(Integer.toString(pageno)).
                concat(String.format("/?sortfield=%s&sortasc=%b", sortfield, orderAsc));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DISPATCHER')")
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

    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ModelAndView errorHandler(HttpServletRequest request, org.springframework.dao.DataIntegrityViolationException e
    ) {/*
        process duplicates, some fields should be unique
       */

        ModelAndView model = new ModelAndView("airportedit");
        Airport airport = new Airport();
        model.addObject("sortfield", request.getParameter("sortfield"));
        model.addObject("sortasc", request.getParameter("sortasc"));
        airport.setSlat(request.getParameter("slat"));
        airport.setSlon(request.getParameter("slon"));
        airport.setCountry(request.getParameter("country"));
        airport.setFullName(request.getParameter("fullName"));
        airport.setShortName(request.getParameter("shortName"));
        airport.setCity(request.getParameter("city"));
        if ((request.getParameter("id") != null) && (!"0".equals(request.getParameter("id")))) {
            airport.setId(Integer.parseInt(request.getParameter("id")));
        }
        model.addObject("airport", airport);
        model.addObject("exception", e.getMostSpecificCause().getMessage());
        return model;
    }
}