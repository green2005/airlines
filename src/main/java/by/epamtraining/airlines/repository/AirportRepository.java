package by.epamtraining.airlines.repository;

import by.epamtraining.airlines.domain.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AirportRepository extends PagingAndSortingRepository<Airport, Integer> {
    @Query("select count(*) from Airport a")
    long getAirportsCount();
}
