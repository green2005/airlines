package by.epamtraining.airlines.service;

import by.epamtraining.airlines.domain.Airport;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AirportService {
    List<Airport> getAirports();

    List<Airport> getAirports(Integer pageN, Integer pageSize);
    long getAirportsCount();

    void setAirports(List<Airport> airports);
}
