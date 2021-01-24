package by.epamtraining.airlines.service.impl;

import by.epamtraining.airlines.domain.NewsArticle;
import by.epamtraining.airlines.helpers.NewsDataProvider;
import by.epamtraining.airlines.repository.NewsArticleRepository;
import by.epamtraining.airlines.service.NewsArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
public class NewsArticleServiceImpl implements NewsArticleService {


    @Autowired
    NewsArticleRepository newsRepository;

    @Override
    @Transactional
    public List<NewsArticle> getNews(Integer pageNo, Integer pageSize) throws Exception {
        List<NewsArticle> newsList = newsRepository.getAllBetweenNo((pageNo - 1) * pageSize + 1, pageNo * pageSize);
        return newsList;
    }
}
