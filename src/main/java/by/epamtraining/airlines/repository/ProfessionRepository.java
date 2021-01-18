package by.epamtraining.airlines.repository;

import by.epamtraining.airlines.domain.Profession;
import by.epamtraining.airlines.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProfessionRepository extends JpaRepository<Profession, Integer> {

    Profession findByNameAndRank(String name, String rank);
}
