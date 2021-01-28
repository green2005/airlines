package by.epamtraining.airlines.controller;

import by.epamtraining.airlines.domain.Airport;
import by.epamtraining.airlines.domain.CrewTypes;
import by.epamtraining.airlines.domain.Flights;
import by.epamtraining.airlines.dto.AirportDTO;
import by.epamtraining.airlines.dto.FlightsDTO;
import by.epamtraining.airlines.dto.FlightsDTOConverter;
import by.epamtraining.airlines.exceptions.DomainNotFoundException;
import by.epamtraining.airlines.exceptions.IncorrectDTOException;
import by.epamtraining.airlines.service.AirportService;
import by.epamtraining.airlines.service.CrewTypesService;
import by.epamtraining.airlines.service.FlightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static by.epamtraining.airlines.AppStarter.RECORDS_PER_PAGE;

@Controller
public class FlightsController {

    @Autowired
    FlightsService flightsService;

    @Autowired
    AirportService airportService;

    @Autowired
    CrewTypesService crewTypesService;

    @Autowired
    FlightsDTOConverter flightsDTOConverter;

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
        List<FlightsDTO> flightsDTOS = flightsPage.getContent().stream().map(FlightsDTO::new).collect(Collectors.toList());
        model.addAttribute("flights", flightsDTOS);
        model.addAttribute("pagecount", totalPageqty);
        model.addAttribute("pageno", n);
        model.addAttribute("recordcount", flightsPage.getTotalElements());
        model.addAttribute("sortfield", sortfield);
        model.addAttribute("sortasc", orderAsc);
        return "flights";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DISPATCHER')")
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

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DISPATCHER')")
    @GetMapping(value = {"/flights/edit/{pageno}/{id}", "/flights/edit", "/flights/edit/{pageno}"})
    public String getFlightsEdit(@PathVariable(required = false) Integer pageno,
                                 @PathVariable(required = false) Integer id,
                                 @RequestParam(required = false, name = "sortfield", defaultValue = "departureTime") String sortfield,
                                 @RequestParam(required = false, name = "sortasc", defaultValue = "true") Boolean orderAsc,
                                 Model model) {
        FlightsDTO flight;
        if (pageno == null) {
            pageno = 1;
        }
        if (id == null) {
            flight = new FlightsDTO();
        } else {
            flight = new FlightsDTO(flightsService.getById(id).orElseThrow(DomainNotFoundException::new));
        }
        List<AirportDTO> airportList = airportService.getAirports().stream().map(AirportDTO::new).collect(Collectors.toList());
        List<CrewTypes> crewTypesList = crewTypesService.getCrewTypes();
        model.addAttribute("crewTypes", crewTypesList);
        model.addAttribute("airports", airportList);
        model.addAttribute("pageno", pageno);
        model.addAttribute("sortfield", sortfield);
        model.addAttribute("sortasc", orderAsc);
        model.addAttribute("flightsDTO", flight);
        return "flightsedit";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DISPATCHER')")
    @PostMapping(value = {"/flights/edit/{pageno}", "/flights/edit"})
    public String postAirportsEdit(@PathVariable(required = false) Integer pageno,
                                   @RequestParam(required = false, name = "sortfield", defaultValue = "departureTime") String sortfield,
                                   @RequestParam(required = false, name = "sortasc", defaultValue = "true") Boolean orderAsc,
                                   @Valid FlightsDTO flights,
                                   BindingResult bindingResult,
                                   Model model
    ) {
        if (pageno == null) {
            pageno = 1;
        }
        if (bindingResult.hasErrors()) {
            List<AirportDTO> airportList = airportService.getAirports().stream().map(AirportDTO::new).collect(Collectors.toList());
            List<CrewTypes> crewTypesList = crewTypesService.getCrewTypes();
            model.addAttribute("crewTypes", crewTypesList);
            model.addAttribute("airports", airportList);
            model.addAttribute("pageno", pageno);
            model.addAttribute("sortfield", sortfield);
            model.addAttribute("sortasc", orderAsc);
            //model.addAttribute("flightsDTO", flight);
            return "flightsedit";
        }
        if (flights.getDepartureAirport().getId() == flights.getDestAirport().getId()) {
            throw new IncorrectDTOException("Destination and departure airports are equal");
        }

        if (flights.getDepartureTime().after(flights.getDestTime())) {
            throw new IncorrectDTOException("Destination time is greater than departure time");
        }

        flightsService.save(flightsDTOConverter.convert(flights));
        return "redirect:/flights/".
                concat(Integer.toString(pageno)).
                concat(String.format("/?sortfield=%s&sortasc=%b", sortfield, orderAsc));
    }


    @ExceptionHandler({org.springframework.dao.DataIntegrityViolationException.class, IncorrectDTOException.class})
    public ModelAndView errorHandler(HttpServletRequest request, Exception e
    ) throws ParseException {/*
        process duplicates, some fields should be unique
       */
        ModelAndView model = new ModelAndView("flightsedit");
        model.addObject("sortfield", request.getParameter("sortfield"));
        model.addObject("sortasc", request.getParameter("sortasc"));

        List<AirportDTO> airportList = airportService.getAirports().stream().map(AirportDTO::new).collect(Collectors.toList());
        List<CrewTypes> crewTypesList = crewTypesService.getCrewTypes();
        model.addObject("crewTypes", crewTypesList);
        model.addObject("airports", airportList);

        FlightsDTO flightsDTO = new FlightsDTO();

        if (request.getParameter("departureAirport.id") != null) {
            Airport departure = airportService.getById(Integer.parseInt(request.getParameter("departureAirport.id"))).get();
            flightsDTO.setDepartureAirport(departure);
        }

        if (request.getParameter("destAirport.id") != null) {
            Airport dest = airportService.getById(Integer.parseInt(request.getParameter("destAirport.id"))).get();
            flightsDTO.setDestAirport(dest);
        }

        if (request.getParameter("crewType.id") != null) {
            int crewTypeId = Integer.parseInt(request.getParameter("crewType.id"));
            flightsDTO.setCrewType(crewTypesService.findById(crewTypeId).get());
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

        if (request.getParameter("departureTime") != null) {
            Date dateTime = simpleDateFormat.parse(request.getParameter("departureTime"));
            flightsDTO.setDepartureTime(dateTime);
        }

        if (request.getParameter("destTime") != null) {
            Date dateTime = simpleDateFormat.parse(request.getParameter("destTime"));
            flightsDTO.setDestTime(dateTime);
        }

        model.addObject("flightsDTO", flightsDTO);
        if (e instanceof org.springframework.dao.DataIntegrityViolationException) {
            model.addObject("exception", ((DataIntegrityViolationException) e).getMostSpecificCause().getMessage());
        } else if (e instanceof IncorrectDTOException) {
            model.addObject("exception", e.getMessage());
        }
        return model;
    }

}
