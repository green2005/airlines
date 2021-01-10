package by.epamtraining.airlines.service;

import by.epamtraining.airlines.domain.Flights;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;


public interface FlightsService {
    List<Flights> getFlights();

    Page<Flights> getFlights(Integer pageN, Integer pageSize, String sortField, Boolean sortAsc);

    Optional<Flights> getById(int id);

    void deleteById(int id);

    void setFlights(List<Flights> flights);

    void save(Flights flights);
}
