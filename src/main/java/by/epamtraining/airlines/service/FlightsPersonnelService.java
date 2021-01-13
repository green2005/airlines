package by.epamtraining.airlines.service;

import by.epamtraining.airlines.domain.Flights;
import by.epamtraining.airlines.domain.Personnel;
import by.epamtraining.airlines.dto.FlightsPersonnelDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/*
    used to work with personnel which is linked to the flight
 */
public interface FlightsPersonnelService {
    List<FlightsPersonnelDTO> getFlightsDTO();

    void setPersonnelForFlight(List<Personnel> personnel, Flights flight);

    void clearPersonnelFromflight(Integer id);

}
