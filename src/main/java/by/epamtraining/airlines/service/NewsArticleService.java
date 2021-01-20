package by.epamtraining.airlines.service;

import by.epamtraining.airlines.domain.NewsArticle;

import java.util.List;

public interface NewsArticleService {
    List<NewsArticle> getNews(Integer pageNo, Integer pageSize) throws Exception;
}
