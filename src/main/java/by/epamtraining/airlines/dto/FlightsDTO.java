package by.epamtraining.airlines.dto;

import by.epamtraining.airlines.domain.Airport;
import by.epamtraining.airlines.domain.CrewTypes;
import by.epamtraining.airlines.domain.Flights;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class FlightsDTO {

    public FlightsDTO() {
    }

    public FlightsDTO(Flights flights) {
        id = flights.getId();
        departureAirport = flights.getDepartureAirport();
        destAirport = flights.getDestAirport();
        crewType = flights.getCrewType();
        departureTime = flights.getDepartureTime();
        destTime = flights.getDestTime();
    }

    public String getFlightShortName() {
        return String.format("%s - %s", departureAirport.getShortName(), destAirport.getShortName());
    }

    public String getFlightFullName() {
        return String.format("%s - %s", departureAirport.getFullName(), destAirport.getFullName());
    }

    private int id;
    @NotNull
    private Airport departureAirport;

    @NotNull
    private Airport destAirport;

    @NotNull
    private CrewTypes crewType;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date departureTime;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date destTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }

    public Airport getDestAirport() {
        return destAirport;
    }

    public void setDestAirport(Airport destAirport) {
        this.destAirport = destAirport;
    }

    public CrewTypes getCrewType() {
        return crewType;
    }

    public void setCrewType(CrewTypes crewType) {
        this.crewType = crewType;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getDestTime() {
        return destTime;
    }

    public void setDestTime(Date destTime) {
        this.destTime = destTime;
    }
}
