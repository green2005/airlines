package by.epamtraining.airlines.repository;

import by.epamtraining.airlines.domain.Personnel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PersonnelRepository extends JpaRepository<Personnel, Integer> {
    @Query("select p from Personnel p inner join p.profession prof where prof.name=?1 and prof.rank=?2")
    List<Personnel> getByProfession(String professionName, String rank);

    @Query(value = "select p from Flights fl join fl.flightPersonnel p where p.id in (:list) and " +
            " fl.id<>:id and " +
            " ((fl.destTime BETWEEN :d1 AND :d2) OR " +
            " (fl.departureTime BETWEEN :d1 AND :d2) OR " +
            " (:d1 BETWEEN fl.departureTime AND fl.destTime) OR" +
            " (:d2 BETWEEN fl.departureTime AND fl.destTime)) "
    )
    List<Personnel> getBusyPersonnel(@Param("list") List<Integer> list,
                                     @Param("id") Integer flightId,
                                     @Param("d1") Date d1,
                                     @Param("d2") Date d2);

    List<Personnel> getByLastName(String lastName);
}
