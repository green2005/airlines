package by.epamtraining.airlines.controller;

import by.epamtraining.airlines.exceptions.DomainNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
public class ExceptionController {

    @ExceptionHandler(DomainNotFoundException.class)
    public String handleDomainNotFoundException(Model model, Exception ex) {
        //TODO: add to logger
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String deefaultHandleException(Model model, Exception ex) {
        //TODO: add to logger
        return "error";
    }

}
