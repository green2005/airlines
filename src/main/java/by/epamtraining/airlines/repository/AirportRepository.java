package by.epamtraining.airlines.repository;

import by.epamtraining.airlines.domain.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AirportRepository extends JpaRepository<Airport, Integer> {
    @Query("select count(*) from Airport a")
    long getAirportsCount();
}
