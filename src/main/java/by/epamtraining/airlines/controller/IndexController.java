package by.epamtraining.airlines.controller;

import by.epamtraining.airlines.domain.NewsArticle;
import by.epamtraining.airlines.helpers.NewsDataProvider;
import by.epamtraining.airlines.service.NewsArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static by.epamtraining.airlines.AppStarter.RECORDS_PER_PAGE;

@Controller
public class IndexController {

    @Autowired
    NewsArticleService newsService;

    @GetMapping(value = {"/", "/index", "/{n}", "/index/{n}"})
    public String getIndexPage(@PathVariable(required = false, name = "n") Integer n,
                               Model model) {
        NewsDataProvider newsProvider = new NewsDataProvider();
        try {
            if (n == null) {
                n = 1;
            }
            List<NewsArticle> articles = newsService.getNews(n, RECORDS_PER_PAGE);
            model.addAttribute("news", articles);
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
