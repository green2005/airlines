package by.epamtraining.airlines.service;

import by.epamtraining.airlines.domain.Airport;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AirportService {
    List<Airport> getAirports();
    void setAirports(List<Airport> airports);
}
