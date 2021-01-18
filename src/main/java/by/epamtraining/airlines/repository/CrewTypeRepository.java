package by.epamtraining.airlines.repository;

import by.epamtraining.airlines.domain.CrewTypes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrewTypeRepository extends JpaRepository<CrewTypes, Integer> {
    CrewTypes findByName(String name);
}
