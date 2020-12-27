package by.epamtraining.airlines.service.impl;

import by.epamtraining.airlines.domain.Airport;
import by.epamtraining.airlines.repository.AirportRepository;
import by.epamtraining.airlines.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportServiceImpl implements AirportService {
    @Autowired
    private AirportRepository airportRepository;

    @Override
    public List<Airport> getAirports() {
        return airportRepository.findAll(Sort.by("fullName"));
    }

    @Override
    public void setAirports(List<Airport> airports) {
        airportRepository.saveAll(airports);
    }
}