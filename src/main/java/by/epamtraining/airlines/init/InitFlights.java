package by.epamtraining.airlines.init;

import by.epamtraining.airlines.domain.CrewTypes;
import by.epamtraining.airlines.domain.Flights;
import by.epamtraining.airlines.domain.Personnel;
import by.epamtraining.airlines.service.AirportService;
import by.epamtraining.airlines.service.CrewTypesService;
import by.epamtraining.airlines.service.FlightsService;
import by.epamtraining.airlines.service.PersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

@Component
public class InitFlights {

    @Autowired
    FlightsService flightsService;

    @Autowired
    CrewTypesService crewTypesService;
    @Autowired
    AirportService airportService;
    @Autowired
    PersonnelService personnelService;

    public void addData() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.ENGLISH);

        Flights flights = new Flights();
        flights.setCrewType(crewTypesService.findByName("clipped"));
        flights.setDestTime(formatter.parse("10-02-2021 15:22:00"));
        flights.setDepartureTime(formatter.parse("10-02-2021 10:30:00"));
        flights.setDepartureAirport(airportService.getByShortName("MSQ"));
        flights.setDestAirport(airportService.getByShortName("VAR"));

        List<Personnel> person = personnelService.getByLastName("Klinton");
        if (!person.isEmpty()) {
            flights.addFlightPersonnel(person.get(0));
        }
        person = personnelService.getByLastName("Boyden");
        if (!person.isEmpty()) {
            flights.addFlightPersonnel(person.get(0));
        }
        person = personnelService.getByLastName("Vazovsky");
        if (!person.isEmpty()) {
            flights.addFlightPersonnel(person.get(0));
        }
        flightsService.save(flights);

        flights = new Flights();
        flights.setCrewType(crewTypesService.findByName("clipped"));
        flights.setDestTime(formatter.parse("10-02-2021 23:42:00"));
        flights.setDepartureTime(formatter.parse("10-02-2021 18:30:00"));
        flights.setDepartureAirport(airportService.getByShortName("VAR"));
        flights.setDestAirport(airportService.getByShortName("MSQ"));
        person = personnelService.getByLastName("Klinton");
        if (!person.isEmpty()) {
            flights.addFlightPersonnel(person.get(0));
        }
        person = personnelService.getByLastName("Boyden");
        if (!person.isEmpty()) {
            flights.addFlightPersonnel(person.get(0));
        }
        person = personnelService.getByLastName("Vazovsky");
        if (!person.isEmpty()) {
            flights.addFlightPersonnel(person.get(0));
        }
        flightsService.save(flights);

        flights = new Flights();
        flights.setCrewType(crewTypesService.findByName("full"));
        flights.setDestTime(formatter.parse("12-02-2021 18:10:00"));
        flights.setDepartureTime(formatter.parse("12-02-2021 11:45:00"));
        flights.setDepartureAirport(airportService.getByShortName("MSQ"));
        flights.setDestAirport(airportService.getByShortName("KIT"));
        flightsService.save(flights);

        flights = new Flights();
        flights.setCrewType(crewTypesService.findByName("full"));
        flights.setDestTime(formatter.parse("12-02-2021 22:10:00"));
        flights.setDepartureTime(formatter.parse("12-02-2021 16:48:00"));
        flights.setDepartureAirport(airportService.getByShortName("MSQ"));
        flights.setDestAirport(airportService.getByShortName("BER"));
        flightsService.save(flights);
    }
}
