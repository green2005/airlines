package by.epamtraining.airlines.helpers;

import by.epamtraining.airlines.controller.IndexController;
import by.epamtraining.airlines.domain.NewsArticle;
import by.epamtraining.airlines.repository.NewsArticleRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static by.epamtraining.airlines.AppStarter.RECORDS_PER_PAGE;


@Component
public class NewsUpdater implements InitializingBean {
    @Autowired
    NewsArticleRepository newsRepository;

    @Autowired
    NewsDataProvider newsDataProvider;


    @Scheduled(fixedDelay = 10000)
    public void checkNewsNeedUpdate() throws Exception {
        if (!isDBCacheValid()) {
            clearNewsCache();
            fillNewsCache();
        }
    }

    private void clearNewsCache() {
        newsRepository.deleteAll();
    }

    private void fillNewsCache() throws Exception {
        for (int i = 1; i <= IndexController.NEWS_PAGE_COUNT; i++) {
            List<NewsArticle> newsList = newsDataProvider.fillFeed(i, RECORDS_PER_PAGE);
            commitArticlesToDB(Collections.synchronizedList(newsList), i, RECORDS_PER_PAGE);
        }
    }

    private void commitArticlesToDB(List<NewsArticle> articles, int pageNo, int pageSize) {
        //add ordering for articles
        //need this for pagination in db cache
        int n = (pageNo - 1) * pageSize + 1;
        for (NewsArticle article : articles) {
            article.setNum(n++);
        }
        newsRepository.saveAll(articles);
    }

    private boolean isDBCacheValid() {
        /*
        check cache.
        if cache is expired, clear it
         */
        NewsArticle dbArticle = newsRepository.findTop1ByOrderByNumAsc();
        if (dbArticle == null) {
            return false;
        }
        List<NewsArticle> inetArticles = null;
        try {
            inetArticles = newsDataProvider.fillFeed(1, 1);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if ((inetArticles != null) && (!inetArticles.isEmpty()) && (dbArticle != null)) {
            if (!inetArticles.get(0).getVkId().equals(dbArticle.getVkId())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        checkNewsNeedUpdate();
    }
}
