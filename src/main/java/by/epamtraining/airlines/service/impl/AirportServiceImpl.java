package by.epamtraining.airlines.service.impl;

import by.epamtraining.airlines.domain.Airport;
import by.epamtraining.airlines.repository.AirportRepository;
import by.epamtraining.airlines.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public Page<Airport> getAirports(Integer pageN, Integer pageSize, String sortField, Boolean sortAsc) {
        Sort sort;
        if (sortAsc) {
            sort = Sort.by(sortField).ascending();
        } else {
            sort = Sort.by(sortField).descending();
        }
        Pageable pageable = PageRequest.of(pageN - 1, pageSize, sort);
        return airportRepository.findAll(pageable);
    }

    @Override
    public Optional<Airport> getById(int id) {
        return airportRepository.findById(id);
    }


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

    @Override
    public void save(Airport airport) {
        airportRepository.save(airport);
    }
}