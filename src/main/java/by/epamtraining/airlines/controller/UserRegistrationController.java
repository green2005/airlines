package by.epamtraining.airlines.controller;

import by.epamtraining.airlines.domain.User;
import by.epamtraining.airlines.dto.UserDTOConverter;
import by.epamtraining.airlines.dto.UserRegistrationDTO;
import by.epamtraining.airlines.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.TextUtils;

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

    @PostMapping(value = "/register/new")
    public String register(UserRegistrationDTO userDto) {
        userService.addUser(new UserDTOConverter().convert(userDto));
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
            model.addAttribute("error", "pwdsnotequal");
            //TODO: hanlde error in html
            return "passwordresetlast";
        }
        User user = userService.getByEmail(email);
        userService.setUserPwd(user, pwd1);

        return "redirect:/login";
    }
}
