package by.epamtraining.airlines.controller;

import by.epamtraining.airlines.dto.ProfessionDTO;
import by.epamtraining.airlines.dto.ProfessionDTOConverter;
import by.epamtraining.airlines.exceptions.DomainNotFoundException;
import by.epamtraining.airlines.service.ProfessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProfessionsController {

    @Autowired
    ProfessionService professionService;

    @Autowired
    ProfessionDTOConverter professionDTOConverter;

    @GetMapping(value = "/professions")
    public String getProfessions(Model model) {
        List<ProfessionDTO> professions = professionService.getProfessions().stream().map(ProfessionDTO::new).collect(Collectors.toList());
        model.addAttribute("professions", professions);
        return "professions";
    }

    // @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DISPATCHER')")
    @GetMapping(value = {"/professions/edit/{id}", "/professions/edit"})
    public String getProfessionsEdit(@PathVariable(required = false) Integer id, Model model) {
        ProfessionDTO profession;
        if ((id == null) || (id == 0)) {
            profession = new ProfessionDTO();
        } else {
            profession = new ProfessionDTO(professionService.getProfessionById(id).orElseThrow(DomainNotFoundException::new));
        }
        model.addAttribute("id", id);
        model.addAttribute("professionDTO", profession);
        return "professionedit";
    }

    // @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DISPATCHER')")
    @PostMapping(value = {"/professions/edit/{id}", "/professions/edit"})
    public String postProfessionsEdit(@PathVariable(required = false) Integer id,
                                      @Valid ProfessionDTO professionDTO,
                                      BindingResult bindingResult,
                                      Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "professionedit";
        }

        professionService.saveProfession(professionDTOConverter.convert(professionDTO));
        return "redirect:/professions";
    }

    //  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DISPATCHER')")
    @PostMapping(value = "/professions/delete/{id}")
    public String getProfessionsDelete(@PathVariable(required = true) Integer id, Model model) {
        professionService.deleteProfession(id);
        return "redirect:/professions";
    }

    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ModelAndView errorHandler(HttpServletRequest request, org.springframework.dao.DataIntegrityViolationException e
    ) {
        ModelAndView modelAndView = new ModelAndView("professionedit");
        ProfessionDTO profession = new ProfessionDTO();
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
        modelAndView.addObject("professionDTO", profession);
        modelAndView.addObject("exception", e.getMostSpecificCause().getMessage());
        return modelAndView;
    }
}
