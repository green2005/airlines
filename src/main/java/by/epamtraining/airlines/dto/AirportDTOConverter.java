package by.epamtraining.airlines.dto;

import by.epamtraining.airlines.domain.Airport;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AirportDTOConverter implements Converter<AirportDTO, Airport> {

    @Override
    public Airport convert(AirportDTO airportDTO) {
        Airport airport = new Airport();
        airport.setId(airportDTO.getId());
        airport.setSlon(airportDTO.getSlon());
        airport.setSlat(airportDTO.getSlat());
        airport.setFullName(airportDTO.getFullName());
        airport.setShortName(airportDTO.getShortName());
        airport.setCity(airportDTO.getCity());
        airport.setCountry(airportDTO.getCountry());
        airport.setId(airportDTO.getId());
        return airport;
    }
}
