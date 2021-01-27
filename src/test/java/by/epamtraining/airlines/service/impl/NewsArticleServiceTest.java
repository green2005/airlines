package by.epamtraining.airlines.service.impl;

import by.epamtraining.airlines.domain.NewsArticle;
import by.epamtraining.airlines.service.NewsArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class NewsArticleServiceTest {

    @Autowired
    private NewsArticleService newsArticleService;

    @Test
    public void contextLoads() throws Exception {
        assertThat(newsArticleService).isNotNull();
    }

    @Test
    public void getSomeContent() throws Exception {
        List<NewsArticle> newsList = newsArticleService.getNews(1, 1);
        assertThat(newsList.size() == 1);
    }
}
