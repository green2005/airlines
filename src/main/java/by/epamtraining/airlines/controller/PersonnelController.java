package by.epamtraining.airlines.controller;

import by.epamtraining.airlines.domain.Personnel;
import by.epamtraining.airlines.domain.Profession;
import by.epamtraining.airlines.dto.PersonnelDTO;
import by.epamtraining.airlines.dto.PersonnelDTOConverter;
import by.epamtraining.airlines.exceptions.DomainNotFoundException;
import by.epamtraining.airlines.service.PersonnelService;
import by.epamtraining.airlines.service.ProfessionService;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import static by.epamtraining.airlines.AppStarter.RECORDS_PER_PAGE;

@Controller
public class PersonnelController {
    @Autowired
    private PersonnelService personnelService;

    @Autowired
    ProfessionService professionService;

    @Autowired
    PersonnelDTOConverter personnelDTOConverter;


    @GetMapping(value = {"/personnel", "/personnel/{n}"})
    public String getPersonnel(@PathVariable(required = false, name = "n") Integer n,
                               @RequestParam(required = false, name = "sortfield", defaultValue = "lastName") String sortfield,
                               @RequestParam(required = false, name = "sortasc", defaultValue = "true") Boolean orderAsc,
                               Model model) {
        if ((n == null) || (n < 1)) {
            n = 1;
        }
        Page<Personnel> personsPage = personnelService.getPersonnel(n, RECORDS_PER_PAGE, sortfield, orderAsc);
        int totalPageqty = personsPage.getTotalPages();
        if ((n > totalPageqty) && (totalPageqty > 0)) {
            n = totalPageqty;
            personsPage = personnelService.getPersonnel(n, RECORDS_PER_PAGE, sortfield, orderAsc);
        }
        List<PersonnelDTO> personnelDTOS = personsPage.getContent().stream().map(PersonnelDTO::new).collect(Collectors.toList());
        model.addAttribute("personnel", personnelDTOS);
        model.addAttribute("pagecount", totalPageqty);
        model.addAttribute("pageno", n);
        model.addAttribute("recordcount", personsPage.getTotalElements());
        model.addAttribute("sortfield", sortfield);
        model.addAttribute("sortasc", orderAsc);
        return "personnellist";
    }

    //  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DISPATCHER')")
    @PostMapping(value = "/personnel/delete/{pageno}/{id}")
    public String deleteAirport(@PathVariable(required = false) Integer pageno,
                                @PathVariable Integer id,
                                @RequestParam(required = false, name = "sortfield", defaultValue = "lastName") String sortfield,
                                @RequestParam(required = false, name = "sortasc", defaultValue = "true") Boolean orderAsc
    ) {
        if (pageno == null) {
            pageno = 1;
        }
        personnelService.deleteById(id);
        return "redirect:/personnel/".
                concat(Integer.toString(pageno)).
                concat(String.format("/?sortfield=%s&sortasc=%b", sortfield, orderAsc));
    }

    // @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DISPATCHER')")
    @GetMapping(value = {"/personnel/edit/{pageno}/{id}", "/personnel/edit/{pageno}", "/personnel/edit"})
    public String getPersonnelEdit(@PathVariable(required = false) Integer pageno,
                                   @PathVariable(required = false) Integer id,
                                   @RequestParam(required = false, name = "sortfield", defaultValue = "lastName") String sortfield,
                                   @RequestParam(required = false, name = "sortasc", defaultValue = "true") Boolean orderAsc,
                                   Model model) {
        PersonnelDTO personnel;
        if (pageno == null) {
            pageno = 1;
        }
        if (id == null) {
            personnel = new PersonnelDTO();
        } else {
            personnel = new PersonnelDTO(personnelService.getById(id).orElseThrow(DomainNotFoundException::new));
        }
        List<Profession> professions = professionService.getProfessions();
        model.addAttribute("professions", professions);
        model.addAttribute("pageno", pageno);
        model.addAttribute("sortfield", sortfield);
        model.addAttribute("sortasc", orderAsc);
        model.addAttribute("personnelDTO", personnel);
        return "personneledit";
    }

    //  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DISPATCHER')")
    @PostMapping(value = {"/personnel/edit/{pageno}/", "/personnel/edit"})
    public String postPersonnelEdit(
            @PathVariable(required = false) Integer pageno,
            @RequestParam(required = false, name = "sortfield", defaultValue = "lastName") String sortfield,
            @RequestParam(required = false, name = "sortasc", defaultValue = "true") Boolean orderAsc,
            @Valid PersonnelDTO personnelDTO,
            BindingResult bindingResult,
            Model model
    ) {
        if (pageno == null) {
            pageno = 1;
        }
        if (bindingResult.hasErrors()) {
            List<Profession> professions = professionService.getProfessions();
            model.addAttribute("professions", professions);
            model.addAttribute("pageno", pageno);
            model.addAttribute("sortfield", sortfield);
            model.addAttribute("sortasc", orderAsc);
            return "personneledit";
        }
        //personnel.setGender(Sex.getFromName(genderName));
        Profession profession = professionService.getProfessionById(personnelDTO.
                getProfession().getId()).
                orElseThrow(() -> new IllegalArgumentException("Unknown profession"));
        personnelDTO.setProfession(profession);
        personnelService.save(personnelDTOConverter.convert(personnelDTO));
        return "redirect:/personnel/".
                concat(Integer.toString(pageno)).
                concat(String.format("/?sortfield=%s&sortasc=%b", sortfield, orderAsc));
    }

    @ExceptionHandler({org.springframework.dao.DataIntegrityViolationException.class,
            IllegalArgumentException.class
    })
    public ModelAndView errorHandler(HttpServletRequest request, org.springframework.dao.DataIntegrityViolationException e
    ) {/*
        process duplicates, errors
       */
        ModelAndView model = new ModelAndView("personneledit");
        PersonnelDTO personnel = new PersonnelDTO();
        model.addObject("sortfield", request.getParameter("sortfield"));
        model.addObject("sortasc", request.getParameter("sortasc"));
        personnel.setFirstName(request.getParameter("firstName"));
        personnel.setLastName(request.getParameter("lastName"));
        if (request.getParameter("birthDate") != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                personnel.setBirthDate(sdf.parse(request.getParameter("birthDate")));
            } catch (ParseException parseException) {
            }
        }
        if (request.getParameter("profession.id") != null) {
            int id = Integer.parseInt(request.getParameter("profession.id"));
            Profession profession = professionService.getProfessionById(id).orElseThrow(() ->
                    new IllegalArgumentException("Profession id not found:" + id));
            personnel.setProfession(profession);
        }

        model.addObject("professions", professionService.getProfessions());

        if ((request.getParameter("id") != null) && (!"0".equals(request.getParameter("id")))) {
            personnel.setId(Integer.parseInt(request.getParameter("id")));
        }
        model.addObject("personnelDTO", personnel);
        model.addObject("exception", e.getMostSpecificCause().getMessage());
        return model;
    }

}
