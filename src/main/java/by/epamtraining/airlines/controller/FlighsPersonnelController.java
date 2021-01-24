package by.epamtraining.airlines.controller;

import by.epamtraining.airlines.domain.Flights;
import by.epamtraining.airlines.domain.Personnel;
import by.epamtraining.airlines.domain.Profession;
import by.epamtraining.airlines.dto.FlightsPersonnelDTO;
import by.epamtraining.airlines.exceptions.DomainNotFoundException;
import by.epamtraining.airlines.service.FlightsPersonnelService;
import by.epamtraining.airlines.service.FlightsService;
import by.epamtraining.airlines.service.PersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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

    //@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DISPATCHER')")
    @PostMapping(value = "/flightspersonnel/delete/{id}")
    public String clearPersonnel(@PathVariable Integer id) {
        flightsPersonnelService.clearPersonnelFromflight(id);
        return "redirect:/flightspersonnel";
    }

   // @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DISPATCHER')")
    @GetMapping(value = "/flightspersonnel/edit/{id}")
    public String editPersonnelForFlight(@PathVariable Integer id, Model model) {
        return getViewEditName(id, model, true);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DISPATCHER')")
    @PostMapping(value = "/flightspersonnel/edit")
    public String postPersonnelforFlight(
            HttpServletRequest request,
            Model model,
            Integer flightid
    ) {
        Flights flights = flightsService.getById(flightid).orElseThrow(() -> new DomainNotFoundException());
        List<Personnel> list = new ArrayList<>();
        for (String s : request.getParameterValues("id")) {
            Personnel personnel = personnelService.getById(Integer.parseInt(s)).orElseThrow(
                    () -> new DomainNotFoundException()
            );

            if (list.contains(personnel)) {
                List<String> e = new ArrayList<>();
                e.add("Emplyoee " + personnel.getFirstName() + " " +
                        personnel.getLastName() + " occures twice");
                model.addAttribute("exception", e);
            }
            list.add(personnel);
        }


        if (!model.containsAttribute("exception")) {
            List<Integer> idsList = new ArrayList<>();
            list.stream().forEach(item -> idsList.add(item.getId()));
            List<Personnel> busyList = personnelService.getBusyPersonnel(idsList, flights.getId(), flights.getDepartureTime(), flights.getDestTime());
            if (busyList.size() > 0) {
                List<String> exceptions = new ArrayList<>();
                for (Personnel personnel : busyList) {
                    exceptions.add(personnel.getFirstName().concat(" ").concat(personnel.getLastName()).concat(" is busy at that time"));
                }
                model.addAttribute("exception", exceptions);
            }
        }

        if (model.containsAttribute("exception")) {
            String viewName = getViewEditName(flightid, model, false);
            //fill user edited employee
            Map<Profession, Integer> professionsMap = new HashMap<>();
            for (Personnel personnel : list) {
                int n = 0;
                if (professionsMap.get(personnel.getProfession()) != null) {
                    n = professionsMap.get(personnel.getProfession());
                }
                n += 1;
                if (n == 1) {
                    model.addAttribute(personnel.getProfession().getName(), personnel);
                } else {
                    model.addAttribute(personnel.getProfession().getName() + n, personnel);
                }
                professionsMap.put(personnel.getProfession(), n);
            }
            return viewName;
        }

        flights.addFlightPersonnel(list);
        flightsService.save(flights);
        return "redirect:/flightspersonnel";
    }

    String getViewEditName(Integer id, Model model, boolean addEmployee) {
        Flights flight = flightsService.getById(id).orElseThrow(() -> new DomainNotFoundException());
        List<Personnel> pilotList = personnelService.getByProfession("pilot", "middle");
        List<Personnel> airHostessList = personnelService.getByProfession("air hostess", "middle");
        List<Personnel> navigatorList = personnelService.getByProfession("navigator", "middle");
        List<Personnel> personnelList = flight.getFlightPersonnel();
        model.addAttribute("pilotlist", pilotList);
        model.addAttribute("airhostesslist", airHostessList);
        model.addAttribute("navigatorlist", navigatorList);
        model.addAttribute("flight", flight);

        Personnel pilot = null;
        Personnel pilot2 = null;
        Personnel airHostess = null;
        Personnel navigator = null;

        for (Personnel p : personnelList) {
            if ("pilot".equalsIgnoreCase(p.getProfession().getName())) {
                if (pilot == null) {
                    pilot = p;
                } else {
                    pilot2 = p;
                }
            } else if ("air hostess".equalsIgnoreCase(p.getProfession().getName())) {
                airHostess = p;
            } else if ("navigator".equalsIgnoreCase(p.getProfession().getName())) {
                navigator = p;
            }
        }
        if (pilot == null) {
            pilot = new Personnel();
        }
        if (pilot2 == null) {
            pilot2 = new Personnel();
        }
        if (airHostess == null) {
            airHostess = new Personnel();
        }
        if (navigator == null) {
            navigator = new Personnel();
        }

        if (addEmployee) {
            model.addAttribute("pilot", pilot);
            model.addAttribute("air hostess", airHostess);
            model.addAttribute("navigator", navigator);
        }
        if ("clipped".equalsIgnoreCase(flight.getCrewType().getName())) {
            return "flightpersonneleditclipped";
        } else {
            if (addEmployee) {
                model.addAttribute("pilot2", pilot2);
            }
            return "flightpersonneleditfull";
        }
    }
}