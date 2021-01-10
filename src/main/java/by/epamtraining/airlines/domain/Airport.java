package by.epamtraining.airlines.domain;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Size(min = 2, max = 20)
    @Column(unique = true)
    private String shortName;

    @Column(unique = true)
    @Size(min = 2, max = 255)
    private String fullName;

    private String country;

    private String city;

    private String slat;

    private String slon;

    public int getId() {
        return id;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setId(int id) {
        this.id = id;
    }
}
