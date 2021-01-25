package by.epamtraining.airlines.controller;

import by.epamtraining.airlines.domain.Airport;
import by.epamtraining.airlines.dto.AirportDTO;
import by.epamtraining.airlines.dto.AirportDTOConverter;
import by.epamtraining.airlines.exceptions.DomainNotFoundException;
import by.epamtraining.airlines.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static by.epamtraining.airlines.AppStarter.RECORDS_PER_PAGE;

@Controller
public class AirportController {

    @Autowired
    private AirportService airportService;

    @Autowired
    private AirportDTOConverter airportDTOConverter;

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
        List<AirportDTO> airportDTOS = airportPage.getContent().stream().map(AirportDTO::new).collect(Collectors.toList());
        model.addAttribute("airports", airportDTOS);
        model.addAttribute("pagecount", totalPageqty);
        model.addAttribute("pageno", n);
        model.addAttribute("recordcount", airportPage.getTotalElements());
        model.addAttribute("sortfield", sortfield);
        model.addAttribute("sortasc", orderAsc);
        return "airportlist";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DISPATCHER')")
    @GetMapping(value = {"/airports/edit/{pageno}/{id}", "/airports/edit/{pageno}"})
    public String getAirportsEdit(@PathVariable(required = false) Integer pageno,
                                  @PathVariable(required = false) Integer id,
                                  @RequestParam(required = false, name = "sortfield", defaultValue = "shortName") String sortfield,
                                  @RequestParam(required = false, name = "sortasc", defaultValue = "true") Boolean orderAsc,
                                  Model model) {

        if (pageno == null) {
            pageno = 1;
        }
        AirportDTO airportDTO = null;
        if (id != null) {
            Airport airport = airportService.getById(id).orElseThrow(DomainNotFoundException::new);
            airportDTO = new AirportDTO(airport);
        } else {
            airportDTO = new AirportDTO();
        }
        model.addAttribute("airportDTO", airportDTO);
        model.addAttribute("pageno", pageno);
        model.addAttribute("sortfield", sortfield);
        model.addAttribute("sortasc", orderAsc);
        return "airportedit";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DISPATCHER')")
    @PostMapping(value = {"/airports/edit/{pageno}", "/airports/edit"})
    public String postAirportsEdit(@PathVariable(required = false) Integer pageno,
                                   @RequestParam(required = false, name = "sortfield", defaultValue = "shortName") String sortfield,
                                   @RequestParam(required = false, name = "sortasc", defaultValue = "true") Boolean orderAsc,
                                   @Valid AirportDTO airport,
                                   BindingResult bindingResult,
                                   Model model
    ) {
        if (pageno == null) {
            pageno = 1;
        }
        ///airports/edit/3/?sortfield=city&sortasc=true

        if (bindingResult.hasErrors()) {
            model.addAttribute("pageno", pageno);
            model.addAttribute("sortfield", sortfield);
            model.addAttribute("sortasc", orderAsc);
            return "airportedit";
        }
        airportService.save(airportDTOConverter.convert(airport));
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
        AirportDTO airport = new AirportDTO();
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
        model.addObject("airportDTO", airport);
        model.addObject("exception", e.getMostSpecificCause().getMessage());

        return model;
    }
}