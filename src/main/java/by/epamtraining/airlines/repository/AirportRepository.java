package by.epamtraining.airlines.repository;

import by.epamtraining.airlines.domain.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<Airport, Integer> {
}
