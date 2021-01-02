package by.epamtraining.airlines.service.impl;

import by.epamtraining.airlines.domain.Airport;
import by.epamtraining.airlines.repository.AirportRepository;
import by.epamtraining.airlines.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AirportServiceImpl implements AirportService {
    @Autowired
    private AirportRepository airportRepository;

    @Override
    public List<Airport> getAirports() {
        List<Airport> airports = new ArrayList<>();
        airportRepository.findAll(Sort.by("fullname")).forEach(item -> {
            airports.add(item);
        });
        return airports;
    }

    @Override
    public List<Airport> getAirports(Integer pageN, Integer pageSize) {
        Pageable page = PageRequest.of(pageN, pageSize, Sort.by("fullName"));
        List<Airport> airports = new ArrayList<>();
        airportRepository.findAll(page).forEach(item -> airports.add(item));
        return airports;
    }

    //long countByAreaCode(String code);

    @Override
    public long getAirportsCount() {
        return airportRepository.getAirportsCount();
    }

    @Override
    public void deleteById(int id) {
        airportRepository.deleteById(id);
    }

    @Override
    public void setAirports(List<Airport> airports) {
        airportRepository.saveAll(airports);
    }
}