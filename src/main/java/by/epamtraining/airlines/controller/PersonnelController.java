package by.epamtraining.airlines.controller;

import by.epamtraining.airlines.domain.Personnel;
import by.epamtraining.airlines.domain.Profession;
import by.epamtraining.airlines.domain.Sex;
import by.epamtraining.airlines.exceptions.DomainNotFoundException;
import by.epamtraining.airlines.service.PersonnelService;
import by.epamtraining.airlines.service.ProfessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UnknownFormatConversionException;

import static by.epamtraining.airlines.AppStarter.RECORDS_PER_PAGE;

@Controller
public class PersonnelController {
    @Autowired
    private PersonnelService personnelService;

    @Autowired
    ProfessionService professionService;

    /*
       TODO: uncomment it, need Role checks
        @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DISPATCHER') or hasRole('ROLE_USER')")
     */
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
        if (n > totalPageqty) {
            n = totalPageqty;
            personsPage = personnelService.getPersonnel(n, RECORDS_PER_PAGE, sortfield, orderAsc);
        }
        model.addAttribute("personnel", personsPage.getContent());
        model.addAttribute("pagecount", totalPageqty);
        model.addAttribute("pageno", n);
        model.addAttribute("recordcount", personsPage.getTotalElements());
        model.addAttribute("sortfield", sortfield);
        model.addAttribute("sortasc", orderAsc);
        return "personnellist";
    }

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

    @GetMapping(value = {"/personnel/edit/{pageno}/{id}", "/personnel/edit/{pageno}", "/personnel/edit"})
    public String getPersonnelEdit(@PathVariable(required = false) Integer pageno,
                                   @PathVariable(required = false) Integer id,
                                   @RequestParam(required = false, name = "sortfield", defaultValue = "lastName") String sortfield,
                                   @RequestParam(required = false, name = "sortasc", defaultValue = "true") Boolean orderAsc,
                                   Model model) {
        Personnel personnel;
        if (pageno == null) {
            pageno = 1;
        }
        if (id == null) {
            personnel = new Personnel();
        } else {
            personnel = personnelService.getById(id).orElseThrow(DomainNotFoundException::new);
        }
        List<Profession> professions = professionService.getProfessions();
        model.addAttribute("professions", professions);
        model.addAttribute("pageno", pageno);
        model.addAttribute("sortfield", sortfield);
        model.addAttribute("sortasc", orderAsc);
        model.addAttribute("personnel", personnel);
        return "personneledit";
    }

    @PostMapping(value = {"/personnel/edit/{pageno}/", "/personnel/edit"})
    public String postPersonnelEdit(
            @PathVariable(required = false) Integer pageno,
            @RequestParam(required = false, name = "sortfield", defaultValue = "lastName") String sortfield,
            @RequestParam(required = false, name = "sortasc", defaultValue = "true") Boolean orderAsc,
            @Valid Personnel personnel
    ) {
        if (pageno == null) {
            pageno = 1;
        }
        //personnel.setGender(Sex.getFromName(genderName));
        Profession profession = professionService.getProfessionById(personnel.
                getProfession().getId()).
                orElseThrow(() -> new IllegalArgumentException("Unknown profession"));
        personnel.setProfession(profession);
        personnelService.save(personnel);
        return "redirect:/personnel/".
                concat(Integer.toString(pageno)).
                concat(String.format("/?sortfield=%s&sortasc=%b", sortfield, orderAsc));
    }

        @InitBinder
        public void initBinder(WebDataBinder binder) {
            CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
            binder.registerCustomEditor(Date.class, editor);
        }

    @ExceptionHandler({org.springframework.dao.DataIntegrityViolationException.class,
            IllegalArgumentException.class
    })
    public ModelAndView errorHandler(HttpServletRequest request, org.springframework.dao.DataIntegrityViolationException e
    ) {/*
        process duplicates, errors
       */
        ModelAndView model = new ModelAndView("personneledit");
        Personnel personnel = new Personnel();
        model.addObject("sortfield", request.getParameter("sortfield"));
        model.addObject("sortasc", request.getParameter("sortasc"));
        personnel.setFirstName(request.getParameter("firstName"));
        personnel.setLastName(request.getParameter("lastName"));
        if ((request.getParameter("birthDate") != null) && (!request.getParameter("birthDate").equals(""))) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date d = sdf.parse(request.getParameter("birthDate"));
                personnel.setBirthDate(d);
            } catch (Exception dateException) {
            }
        }


        if ((request.getParameter("id") != null) && (!"0".equals(request.getParameter("id")))) {
            personnel.setId(Integer.parseInt(request.getParameter("id")));
        }
        model.addObject("personnel", personnel);
        model.addObject("exception", e.getMostSpecificCause().getMessage());
        return model;
    }

}
