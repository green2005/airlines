package by.epamtraining.airlines.controller;

import by.epamtraining.airlines.domain.News;
import by.epamtraining.airlines.helpers.NewsDataProvider;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class IndexController {

    @GetMapping(value = {"/", "/index", "/{n}", "/index/{n}"})
    public String getIndexPage(@PathVariable(required = false, name = "n") Integer n,
                               Model model) {
        NewsDataProvider newsProvider = new NewsDataProvider();
        try {
            if (n == null) {
                n = 1;
            }
            List<News> newsList = newsProvider.fillFeed(n);
            model.addAttribute("news", newsList);
            model.addAttribute("pagecount", 10);
            model.addAttribute("pageno", n);
        } catch (Exception exception) {
            model.addAttribute("exception", exception.getMessage());
        }
        return "index";
    }

    @GetMapping(value = "/login")
    public String getLogin(){

        return "login";
    }

    @GetMapping(value = "register")
    public  String getRegister(){
        return "register";
    }

}
