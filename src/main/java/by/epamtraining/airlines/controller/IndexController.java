package by.epamtraining.airlines.controller;

import by.epamtraining.airlines.domain.NewsArticle;
import by.epamtraining.airlines.helpers.NewsDataProvider;
import by.epamtraining.airlines.service.NewsArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static by.epamtraining.airlines.AppStarter.RECORDS_PER_PAGE;

@Controller
public class IndexController {

    @Autowired
    NewsArticleService newsService;

    public static final int NEWS_PAGE_COUNT = 10;

    @GetMapping(value = {"/", "/index", "/{n}", "/index/{n}"})
    public String getIndexPage(@PathVariable(required = false, name = "n") Integer n,
                               Model model) {
        NewsDataProvider newsProvider = new NewsDataProvider();
        List<NewsArticle> articles = null;
        if (n == null) {
            n = 1;
        }
        try {
            articles = newsService.getNews(n, RECORDS_PER_PAGE);
        } catch (Exception exception) {
            model.addAttribute("exception", exception.getMessage());
        }
        model.addAttribute("news", articles);
        model.addAttribute("pagecount", NEWS_PAGE_COUNT);
        model.addAttribute("pageno", n);
        return "index";
    }

    @GetMapping(value = "/login")
    public String getLogin(Model model) {
        return "login";
    }
}
