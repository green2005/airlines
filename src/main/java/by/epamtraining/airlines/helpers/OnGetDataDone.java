package by.epamtraining.airlines.helpers;

import by.epamtraining.airlines.domain.News;

import java.util.List;

public interface OnGetDataDone {
    void onDone(Exception e, List<News> responseList);
}
