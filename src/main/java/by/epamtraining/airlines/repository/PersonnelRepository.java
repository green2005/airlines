package by.epamtraining.airlines.repository;

import by.epamtraining.airlines.domain.Personnel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonnelRepository extends JpaRepository<Personnel, Integer> {
    @Query("select p from Personnel p inner join p.profession prof where prof.name=?1 and prof.rank=?2")
    List<Personnel> getByProfession(String professionName, String rank);
}
