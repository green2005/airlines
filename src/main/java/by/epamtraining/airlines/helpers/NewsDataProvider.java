package by.epamtraining.airlines.helpers;

import by.epamtraining.airlines.domain.NewsArticle;
import org.springframework.stereotype.Component;

;import java.util.List;

@Component
public class NewsDataProvider {
    public List<NewsArticle> fillFeed(int pageNo, int pageSize) throws Exception {
        final Object o = new Object();
        List<NewsArticle> response = null;
        String reponseStr = HttpDataProvider.getJSONStringFromUrl(API.getVKUrl(pageNo-1, pageSize));
        response = new NewsDataProcessor().getVKData(reponseStr);
        return response;
    }
}
