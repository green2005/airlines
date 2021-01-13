package by.epamtraining.airlines.service.impl;

import by.epamtraining.airlines.domain.Flights;
import by.epamtraining.airlines.domain.Personnel;
import by.epamtraining.airlines.dto.FlightsPersonnelDTO;
import by.epamtraining.airlines.repository.FlightsRepository;
import by.epamtraining.airlines.service.FlightsPersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightsPersonnnelImpl implements FlightsPersonnelService {
    @Autowired
    FlightsRepository flightsRepository;

    @Override
    public List<FlightsPersonnelDTO> getFlightsDTO() {
        return flightsRepository.getFlights();
    }

    @Override
    public void setPersonnelForFlight(List<Personnel> personnel, Flights flight) {
        //
    }

    @Override
    public void clearPersonnelFromflight(Integer id) {
        flightsRepository.clearPersonnelForFlight(id);
    }
}
