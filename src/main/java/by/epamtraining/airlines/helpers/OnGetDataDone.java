package by.epamtraining.airlines.helpers;

import by.epamtraining.airlines.domain.NewsArticle;

import java.util.List;

public interface OnGetDataDone {
    void onDone(Exception e, List<NewsArticle> responseList);
}
