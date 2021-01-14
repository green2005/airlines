package by.epamtraining.airlines.controller;

import by.epamtraining.airlines.domain.Flights;
import by.epamtraining.airlines.domain.Personnel;
import by.epamtraining.airlines.dto.FlightsPersonnelDTO;
import by.epamtraining.airlines.exceptions.DomainNotFoundException;
import by.epamtraining.airlines.service.FlightsPersonnelService;
import by.epamtraining.airlines.service.FlightsService;
import by.epamtraining.airlines.service.PersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Controller
public class FlighsPersonnelController {

    @Autowired
    FlightsPersonnelService flightsPersonnelService;

    @Autowired
    PersonnelService personnelService;

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
        List<Personnel> pilotList = personnelService.getByProfession("pilot", "middle");
        List<Personnel> airHostessList = personnelService.getByProfession("air hostess", "middle");
        List<Personnel> radiomanList = personnelService.getByProfession("radioman", "middle");
        List<Personnel> navigatorList = personnelService.getByProfession("navigator", "middle");
        List<Personnel> personnelList = flight.getFlightPersonnel();
        if ("clipped".equalsIgnoreCase(flight.getCrewType().getName())) {
            model.addAttribute("pilotlist", pilotList);
            model.addAttribute("airhostesslist", airHostessList);
            model.addAttribute("navigatorlist", navigatorList);
            model.addAttribute("flight", flight);
            AtomicReference<Personnel> pilot = new AtomicReference<>(null);
            AtomicReference<Personnel> airHostess = new AtomicReference<>(null);
            AtomicReference<Personnel> navigator = new AtomicReference<>(null);

            personnelList.forEach(item -> {
                if (
                        (item.getProfession().getName().equalsIgnoreCase("pilot")) &&
                                (item.getProfession().getRank().equalsIgnoreCase("middle"))) {

                    pilot.set(item);
                }

                if ((item.getProfession().getName().equalsIgnoreCase("air hostess")) &&
                        (item.getProfession().getRank().equalsIgnoreCase("middle"))) {
                    airHostess.set(item);
                }

                if ((item.getProfession().getName().equalsIgnoreCase("navigator")) &&
                        (item.getProfession().getRank().equalsIgnoreCase("middle"))) {
                    navigator.set(item);
                }
            });
            model.addAttribute("pilot", pilot.get());
            model.addAttribute("airhostess", airHostess.get());
            model.addAttribute("navigator", navigator.get());
            return "flightpersonneleditclipped";
        } else {

            return "flightpersonneledfull";
        }
    }

    @PostMapping(value = "/flightspersonnel/edit")
    public String postPersonnelforFlight(
            HttpServletRequest request,
            Integer flightid
    ) {
        Flights flights = flightsService.getById(flightid).orElseThrow(() -> new DomainNotFoundException());
        List<Personnel> list = new ArrayList<>();
        for (String s : request.getParameterValues("id")) {
            list.add(personnelService.getById(Integer.parseInt(s)).orElseThrow(
                    () -> new DomainNotFoundException()
            ));
        }
        flights.addFlightPersonnel(list);
        flightsService.save(flights);
        return "redirect:/flightspersonnel";
    }
}
