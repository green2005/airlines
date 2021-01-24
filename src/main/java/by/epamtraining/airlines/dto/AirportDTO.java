package by.epamtraining.airlines.dto;

import by.epamtraining.airlines.domain.Airport;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AirportDTO {

    private int id;

    @NotNull
    @Size(min = 3, max = 5)
    private String shortName;

    @NotNull
    @Size(min = 3, max = 50)
    private String fullName;

    @NotNull
    @Size(min = 2, max = 50)
    private String country;

    @NotNull
    @Size(min = 3, max = 50)
    private String city;

    private String slat;

    private String slon;

    public AirportDTO() {
    }

    ;

    public AirportDTO(Airport airport) {
        id = airport.getId();
        slat = airport.getSlat();
        slon = airport.getSlon();
        fullName = airport.getFullName();
        shortName = airport.getShortName();
        country = airport.getCountry();
        city = airport.getCity();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSlat() {
        return slat;
    }

    public void setSlat(String slat) {
        this.slat = slat;
    }

    public String getSlon() {
        return slon;
    }

    public void setSlon(String slon) {
        this.slon = slon;
    }
}
