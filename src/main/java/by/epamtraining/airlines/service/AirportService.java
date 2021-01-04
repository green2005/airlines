package by.epamtraining.airlines.service;

import by.epamtraining.airlines.domain.Airport;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface AirportService {
    List<Airport> getAirports();

    List<Airport> getAirports(Integer pageN, Integer pageSize);

    Optional<Airport> getById(int id);

    long getAirportsCount();

    void deleteById(int id);

    void setAirports(List<Airport> airports);

    void save(Airport airport);
}
