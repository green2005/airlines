package by.epamtraining.airlines.repository;

import by.epamtraining.airlines.domain.NewsArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface NewsArticleRepository extends JpaRepository<NewsArticle, Integer> {
    NewsArticle findTopByOrderByDateDesc();

    @Query(value = "from NewsArticle t where t.num BETWEEN :startNo AND :endNo ORDER BY num")
    List<NewsArticle> getAllBetweenNo(@Param("startNo") Integer startNo, @Param("endNo") Integer endNo);

    NewsArticle findTop1ByOrderByNumAsc();
}
