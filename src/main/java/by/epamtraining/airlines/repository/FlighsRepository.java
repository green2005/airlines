package by.epamtraining.airlines.repository;

import by.epamtraining.airlines.domain.Flights;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlighsRepository extends JpaRepository<Flights, Integer> {
}
