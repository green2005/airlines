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
    NewsDataProvider newsDataProvider;

    @Autowired
    NewsArticleRepository newsRepository;

    @Override
    @Transactional
    public List<NewsArticle> getNews(Integer pageNo, Integer pageSize) throws Exception {
        checkDBCacheValid();
        List<NewsArticle> newsList = newsRepository.getAllBetweenNo((pageNo - 1) * pageSize + 1, pageNo * pageSize);
        if (newsList.isEmpty()) {
            newsList = newsDataProvider.fillFeed(pageNo, pageSize);
            commitArticlesToCache(Collections.synchronizedList(newsList), pageNo, pageSize);
        }
        return newsList;
    }

    private void commitArticlesToCache(List<NewsArticle> articles, int pageNo, int pageSize) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //add ordering for articles
                //need this for pagination in db cache
                int n = (pageNo - 1) * pageSize + 1;
                for (NewsArticle article : articles) {
                    article.setNum(n++);
                }
                newsRepository.saveAll(articles);
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private void checkDBCacheValid() {
        /*
        check cache.
        if cache is expired, clear it
         */
        NewsArticle dbArticle = newsRepository.findTop1ByOrderByNumAsc();
        if (dbArticle == null) {
            return;
        }
        List<NewsArticle> inetArticles = null;
        try {
            inetArticles = newsDataProvider.fillFeed(1, 1);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if ((inetArticles != null) && (!inetArticles.isEmpty()) && (dbArticle != null)) {
            if (!inetArticles.get(0).getVkId().equals(dbArticle.getVkId())) {
                newsRepository.deleteAll();
            }
        }
    }
}
