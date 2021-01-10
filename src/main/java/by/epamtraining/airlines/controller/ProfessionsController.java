package by.epamtraining.airlines.controller;

import by.epamtraining.airlines.domain.Profession;
import by.epamtraining.airlines.exceptions.DomainNotFoundException;
import by.epamtraining.airlines.service.ProfessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class ProfessionsController {

    @Autowired
    ProfessionService professionService;

    @GetMapping(value = "/professions")
    public String getProfessions(Model model) {
        List<Profession> professions = professionService.getProfessions();
        model.addAttribute("professions", professions);
        return "professions";
    }

    @GetMapping(value = {"/professions/edit/{id}", "/professions/edit"})
    public String getProfessionsEdit(@PathVariable(required = false) Integer id, Model model) {
        Profession profession;
        if ((id == null) || (id == 0)) {
            profession = new Profession();
        } else {
            profession = professionService.getProfessionById(id).orElseThrow(DomainNotFoundException::new);
        }
        model.addAttribute("id", id);
        model.addAttribute("profession", profession);
        return "professionedit";
    }

    @PostMapping(value = {"/professions/edit/{id}", "/professions/edit"})
    public String postProfessionsEdit(@PathVariable(required = false) Integer id, @Valid Profession profession) {
        professionService.saveProfession(profession);
        return "redirect:/professions";
    }

    @PostMapping(value = "/professions/delete/{id}")
    public String getProfessionsDelete(@PathVariable(required = true) Integer id, Model model) {
        professionService.deleteProfession(id);
        return "redirect:/professions";
    }

    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ModelAndView errorHandler(HttpServletRequest request, org.springframework.dao.DataIntegrityViolationException e
    ) {
        ModelAndView modelAndView = new ModelAndView("professionedit");
        Profession profession = new Profession();
        if ((request.getParameter("id") != null) && (!"0".equals(request.getParameter("id")))) {
            profession.setId(Integer.parseInt(request.getParameter("id")));
        }
        profession.setName(request.getParameter("name"));
        profession.setRank(request.getParameter("rank"));
        if ((request.getParameter("hourRate") != null) && (!request.getParameter("hourRate").equals(""))) {
            try {
                profession.setHourRate(new BigDecimal(request.getParameter("hourRate")));
            } catch (NumberFormatException numberFormatException) {
                profession.setHourRate(new BigDecimal(0));
            }
        }
        if ((request.getParameter("id") != null) && (!"0".equals(request.getParameter("id")))) {
            profession.setId(Integer.parseInt(request.getParameter("id")));
        }
        modelAndView.addObject("profession", profession);
        modelAndView.addObject("exception", e.getMostSpecificCause().getMessage());
        return modelAndView;
    }
}
