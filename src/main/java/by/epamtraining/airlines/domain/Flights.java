package by.epamtraining.airlines.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Flights {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne()
    @JoinColumn(name = "departureAirportId")
    private Airport departureAirport;

    @ManyToOne()
    @JoinColumn(name = "destAirportId")
    private Airport destAirport;

    @ManyToOne()
    @JoinColumn(name = "crewTypeId")
    private CrewTypes crewType;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "rlFlightsPersonnel",
            joinColumns = @JoinColumn(name = "personnelId"),
            inverseJoinColumns = @JoinColumn(name = "flightId")
    )
    private List<Personnel> flightPersonnel = new ArrayList();

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date departureTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date destTime;

    @Column(name = "s_distance", nullable = true)
    private Short distance;

    public String getFlightShortName() {
        return String.format("%s - %s", departureAirport.getShortName(), destAirport.getShortName());
    }

    public String getFlightFullName() {
        return String.format("%s - %s", departureAirport.getFullName(), destAirport.getFullName());
    }

    public Flights() {

    }


    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }

    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public void setDestAirport(Airport destAirport) {
        this.destAirport = destAirport;
    }

    public Airport getDestAirport() {
        return destAirport;
    }

    public short getDistance() {
        return distance;
    }

    public void setDistance(short distance) {
        this.distance = distance;
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

    public CrewTypes getCrewType() {
        return crewType;
    }

    public void setCrewType(CrewTypes crewType) {
        this.crewType = crewType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDestTime(Date destTime) {
        this.destTime = destTime;
    }
}
