package by.epamtraining.airlines.service;

import by.epamtraining.airlines.domain.News;
import org.springframework.stereotype.Service;

import java.util.List;

public interface NewsService {
    List<News> getNews();
}
