package by.epamtraining.airlines.controller;

import by.epamtraining.airlines.domain.User;
import by.epamtraining.airlines.dto.UserDTOConverter;
import by.epamtraining.airlines.dto.UserRegistrationDTO;
import by.epamtraining.airlines.exceptions.IncorrectUserInfoException;
import by.epamtraining.airlines.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class UserRegistrationController {

    @Autowired
    UserService userService;

    @GetMapping("/activate")
    String activation(@RequestParam String userid, @RequestParam String code) throws Exception {
        userService.activateUser(userid, code);
        return "redirect:/login";
    }

    @GetMapping(value = "/register")
    public String getRegister(UserRegistrationDTO userRegistrationDTO, Model model) {
        return "register";
    }

    @PostMapping(value = "/register")
    public String register(@Valid UserRegistrationDTO userRegistrationDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            userRegistrationDTO.setPassword("");
            userRegistrationDTO.setPassword2("");
            return "register";
        }
        if (!userRegistrationDTO.getPassword().equals(userRegistrationDTO.getPassword2())) {
            throw new IncorrectUserInfoException("Passwords are not equal");
        }
        User usr = userService.getByEmail(userRegistrationDTO.getEmail());
        if (usr != null) {
            throw new IncorrectUserInfoException("User already exists");
        }
        userService.addUser(new UserDTOConverter().convert(userRegistrationDTO));
        return "redirect:/login";
    }

    @GetMapping(value = "/passwordresetfirst")
    public String getForgetPwd(Model model) {
        return "passwordresetfirst";
    }

    @PostMapping(value = "/resetpwd")
    public String postForgetPwd(@RequestParam(name = "username", required = false) String email, Model model) throws Exception {
        User user = userService.getByEmail(email);
        if (user == null) {
            model.addAttribute("result", "wrongemail");
        } else {
            userService.resetPassword(user);
            model.addAttribute("result", "emailsent");
        }
        return "passwordresetfirst";
    }

    @GetMapping(value = "/passwordresetlast")
    public String getResetPwd(@RequestParam Integer userid, @RequestParam String code, Model model) {
        Optional<User> user = userService.getById(userid);
        if ((!user.isPresent()) || (!user.get().getCRC().equals(code))) {
            //user has been deleted?
            return "redirect:/login";
        } else {
            model.addAttribute("email", user.get().getEmail());
            return "passwordresetlast";
        }
    }

    @PostMapping(value = "/resetpwdlast")
    public String postResetPwd(@RequestParam(name = "email", required = false) String email,
                               @RequestParam(name = "password1", required = false) String pwd1,
                               @RequestParam(name = "password2", required = false) String pwd2,
                               Model model) throws Exception {
        if ((pwd1 == null) || (!pwd1.equals(pwd2))) {
            model.addAttribute("exception", "pwdsnotequal");
            return "passwordresetlast";
        }
        User user = userService.getByEmail(email);
        userService.setUserPwd(user, pwd1);

        return "redirect:/login";
    }

    @ExceptionHandler({org.springframework.dao.DataIntegrityViolationException.class, IncorrectUserInfoException.class})
    public ModelAndView errorHandler(HttpServletRequest request, Exception e
    ) {
        ModelAndView model = new ModelAndView("register");
        UserRegistrationDTO user = new UserRegistrationDTO();
        user.setEmail(request.getParameter("email"));
        user.setName(request.getParameter("name"));
        model.addObject("exception", e.getLocalizedMessage());
        model.addObject("userRegistrationDTO", user);
        return model;
    }

}
