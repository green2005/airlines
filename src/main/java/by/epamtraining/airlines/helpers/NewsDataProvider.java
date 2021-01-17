package by.epamtraining.airlines.helpers;

import by.epamtraining.airlines.domain.News;

;import java.util.List;

public class NewsDataProvider {
    public List<News> fillFeed(int pageNo) throws Exception {
        final Object o = new Object();
        List<News> response = null;
        String reponseStr = HttpDataProvider.getJSONStringFromUrl(API.getVKUrl(pageNo-1));
        response = new NewsDataProcessor().getVKData(reponseStr);
        return response;
    }
}
