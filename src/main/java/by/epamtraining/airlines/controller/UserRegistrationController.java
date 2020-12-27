package by.epamtraining.airlines.controller;

import by.epamtraining.airlines.dto.UserDTOConverter;
import by.epamtraining.airlines.dto.UserRegistrationDTO;
import by.epamtraining.airlines.repository.UserRepository;
import by.epamtraining.airlines.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserRegistrationController {

    @Autowired
    UserService userService;

    @GetMapping("/activate")
    String activation(@RequestParam String userid, @RequestParam String code) throws Exception {
        userService.activateUser(userid, code);
        return "redirect:/login";
    }

    @PostMapping(value = "/register/new")
    public String register(UserRegistrationDTO userDto) {
        userService.addUser(new UserDTOConverter().convert(userDto));
        return "redirect:/login";
    }


}
