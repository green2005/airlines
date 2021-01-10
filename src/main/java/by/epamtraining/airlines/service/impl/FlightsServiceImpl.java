package by.epamtraining.airlines.service.impl;

import by.epamtraining.airlines.domain.Flights;
import by.epamtraining.airlines.repository.FlighsRepository;
import by.epamtraining.airlines.service.FlightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightsServiceImpl implements FlightsService {
    @Autowired
    FlighsRepository flighsRepository;

    @Override
    public List<Flights> getFlights() {
        return flighsRepository.findAll(Sort.by("departureTime"));
    }

    @Override
    public Page<Flights> getFlights(Integer pageN, Integer pageSize, String sortField, Boolean sortAsc) {
        Sort sort;
        if (sortAsc) {
            sort = Sort.by(sortField).ascending();
        } else {
            sort = Sort.by(sortField).descending();
        }
        Pageable pageable = PageRequest.of(pageN - 1, pageSize, sort);
        return flighsRepository.findAll(pageable);
    }

    @Override
    public Optional<Flights> getById(int id) {
        return flighsRepository.findById(id);
    }

    @Override
    public void deleteById(int id) {
        flighsRepository.deleteById(id);
    }

    @Override
    public void setFlights(List<Flights> flights) {
        flighsRepository.saveAll(flights);
    }

    @Override
    public void save(Flights flights) {
        flighsRepository.save(flights);
    }
}
