package by.epamtraining.airlines.controller;

import by.epamtraining.airlines.domain.Airport;
import by.epamtraining.airlines.domain.Flights;
import by.epamtraining.airlines.exceptions.DomainNotFoundException;
import by.epamtraining.airlines.service.FlightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static by.epamtraining.airlines.AppStarter.RECORDS_PER_PAGE;

@Controller
public class FlightsController {

    @Autowired
    FlightsService flightsService;

    @GetMapping(value = {"/flights", "/flights/{n}"})
    public String getFlights(@PathVariable(required = false, name = "n") Integer n,
                             @RequestParam(required = false, name = "sortfield", defaultValue = "departureTime") String sortfield,
                             @RequestParam(required = false, name = "sortasc", defaultValue = "true") Boolean orderAsc,
                             Model model) {
        if ((n == null) || (n < 1)) {
            n = 1;
        }
        Page<Flights> flightsPage = flightsService.getFlights(n, RECORDS_PER_PAGE, sortfield, orderAsc);
        int totalPageqty = flightsPage.getTotalPages();
        if ((n > totalPageqty) && (totalPageqty > 0)) {
            n = totalPageqty;
            flightsPage = flightsService.getFlights(n, RECORDS_PER_PAGE, sortfield, orderAsc);
        }
        model.addAttribute("flights", flightsPage.getContent());
        model.addAttribute("pagecount", totalPageqty);
        model.addAttribute("pageno", n);
        model.addAttribute("recordcount", flightsPage.getTotalElements());
        model.addAttribute("sortfield", sortfield);
        model.addAttribute("sortasc", orderAsc);
        return "flights";
    }

    @PostMapping(value = "/flights/delete/{pageno}/{id}")
    public String deleteAirport(@PathVariable(required = false) Integer pageno,
                                @PathVariable Integer id,
                                @RequestParam(required = false, name = "sortfield", defaultValue = "departureTime") String sortfield,
                                @RequestParam(required = false, name = "sortasc", defaultValue = "true") Boolean orderAsc
    ) {
        if (pageno == null) {
            pageno = 1;
        }
        flightsService.deleteById(id);
        return "redirect:/flights/".
                concat(Integer.toString(pageno)).
                concat(String.format("/?sortfield=%s&sortasc=%b", sortfield, orderAsc));
    }

    @GetMapping(value = {"/flights/edit/{pageno}/{id}", "/flights/edit", "/flights/edit/{pageno}"})
    public String getFlightsEdit(@PathVariable(required = false) Integer pageno,
                                 @PathVariable(required = false) Integer id,
                                 @RequestParam(required = false, name = "sortfield", defaultValue = "shortName") String sortfield,
                                 @RequestParam(required = false, name = "sortasc", defaultValue = "true") Boolean orderAsc,
                                 Model model) {
        Flights flights;
        if (pageno == null) {
            pageno = 1;
        }
        if (id == null) {
            flights = new Flights();
        } else {
            flights = flightsService.getById(id).orElseThrow(DomainNotFoundException::new);
        }
        model.addAttribute("pageno", pageno);
        model.addAttribute("sortfield", sortfield);
        model.addAttribute("sortasc", orderAsc);
        model.addAttribute("flights", flights);
        return "flightsedit";
    }

    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ModelAndView errorHandler(HttpServletRequest request, org.springframework.dao.DataIntegrityViolationException e
    ) {/*
        process duplicates, some fields should be unique
       */
        ModelAndView model = new ModelAndView("airportedit");
        model.addObject("sortfield", request.getParameter("sortfield"));
        model.addObject("sortasc", request.getParameter("sortasc"));
        //todo add flight object
        model.addObject("exception", e.getMostSpecificCause().getMessage());
        return model;
    }


}
