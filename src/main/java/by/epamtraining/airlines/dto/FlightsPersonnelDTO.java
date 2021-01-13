package by.epamtraining.airlines.dto;

import java.util.Date;

public interface FlightsPersonnelDTO {
    public int getId();

    public String getDestAirportShortName();

    public String getDepartureAirportShortName();

    public Date getDestDate();

    public Date getDepartureDate();

    public String getStatus();

    public Integer getPersonsCount();

    public Integer getProfessionCount();
}