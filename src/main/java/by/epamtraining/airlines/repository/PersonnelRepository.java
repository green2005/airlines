package by.epamtraining.airlines.repository;

import by.epamtraining.airlines.domain.Personnel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonnelRepository extends JpaRepository<Personnel, Integer> {

}
