package by.epamtraining.airlines.repository;

import by.epamtraining.airlines.domain.Profession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessionRepository extends JpaRepository<Profession, Integer> {
}
