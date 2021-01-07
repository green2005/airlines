package by.epamtraining.airlines.service;

import by.epamtraining.airlines.domain.Airport;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface AirportService {
    List<Airport> getAirports();

    Page<Airport> getAirports(Integer pageN, Integer pageSize, String sortField, Boolean sortAsc);

    Optional<Airport> getById(int id);

    long getAirportsCount();

    void deleteById(int id);

    void setAirports(List<Airport> airports);

    void save(Airport airport);
}
