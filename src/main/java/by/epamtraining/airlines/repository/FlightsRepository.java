package by.epamtraining.airlines.repository;

import by.epamtraining.airlines.domain.Flights;
import by.epamtraining.airlines.dto.FlightsPersonnelDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface FlightsRepository extends JpaRepository<Flights, Integer> {
/*
    select flights, professions that are needed to the flight, and
    personnel assigned to the flight.
    if all personnel, that is needed, is assigned to the flight - it has status 'filled', else - 'not ready'
 */
    @Query(value = "select\n" +
            "  fl.id,\n" +
            "  adest.short_name as destAirportShortName,\n" +
            "  adep.short_name as departureAirportShortName,\n" +
            "  fl.dest_time as destDate,\n" +
            "  fl.departure_time as departureDate,\n" +
            "  case when prof.qtyprof = persons.qtyPersonnel then 'filled' else 'not ready' end as status,\n" +
            "  prof.qtyProf as professionCount,\n" +
            "  persons.qtyPersonnel as personsCount\n" +
            "from \n" +
            "\t(select \n" +
            "\t fl.id as  flightId ,\n" +
            "\t count(prof.id) as  qtyProf  \n" +
            "\tfrom flights fl   \n" +
            "\t  inner join crew_types ct on ct.id = fl.crew_type_id  \n" +
            "\t  inner join rl_crew_professions rlprof on rlprof.crew_id = fl.crew_type_id\n" +
            "\t  inner join profession prof on prof.id = rlprof.profession_id\t\n" +
            "\t group by fl.id) prof \n" +
            "inner join flights fl on fl.id = prof.flightid\t\n" +
            "inner join airport adest on adest.id = fl.dest_airport_id\n" +
            "inner join airport adep on adep.id = fl.departure_airport_id\n" +
            "left join \t \n" +
            "\t(select \n" +
            "\t fl.id as  flightId ,\n" +
            "\t count(rlpers.personnel_id) as  qtyPersonnel  \n" +
            "\tfrom flights fl   \n" +
            "\t  inner join crew_types ct on ct.id = fl.crew_type_id  \n" +
            "\t  inner join rl_crew_professions rlprof on rlprof.crew_id = fl.crew_type_id\n" +
            "\t  inner join profession prof on prof.id = rlprof.profession_id\n" +
            "\t  inner join personnel persons on persons.profession_id = prof.id \n" +
            "\t  inner join rl_flights_personnel rlpers on rlpers.flight_id = fl.id and rlpers.personnel_id = persons.id \t\n" +
            "\t group by fl.id \n" +
            "\t) persons on persons.flightid = prof.flightid  order by adest.short_name ", nativeQuery = true)
    List<FlightsPersonnelDTO> getFlights();

    @Modifying
    @Transactional
    @Query(value = "delete from rl_flights_personnel where flight_id=?1", nativeQuery = true)
    void clearPersonnelForFlight(Integer id);
}
