package by.epamtraining.airlines.dto;

import by.epamtraining.airlines.domain.Flights;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FlightsDTOConverter implements Converter<FlightsDTO, Flights> {
    @Override
    public Flights convert(FlightsDTO flightsDTO) {
        Flights flights = new Flights();
        flights.setId(flightsDTO.getId());
        flights.setDepartureAirport(flightsDTO.getDepartureAirport());
        flights.setDestAirport(flightsDTO.getDestAirport());
        flights.setCrewType(flightsDTO.getCrewType());
        flights.setDepartureTime(flightsDTO.getDepartureTime());
        flights.setDestTime(flightsDTO.getDestTime());
        return flights;
    }
}
