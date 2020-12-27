package by.epamtraining.airlines.domain;

import org.springframework.lang.Nullable;
import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
public class RegularFlightTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "departureAirportId")
    private Airport departureAirport;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "destAirportId")
    private Airport destAirport;

    @Min(0)
    @Nullable
    private short distance;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "crewId", nullable = false)
    private CrewTypes crewTypes;


    public String getFlightShortName() {
        return String.format("%s - %s", departureAirport.getShortName(), destAirport.getShortName());
    }

    public String getFlightFullName() {
        return String.format("%s - %s", departureAirport.getFullName(), destAirport.getFullName());
    }

    public int getId() {
        return id;
    }

    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public Airport getDestAirport() {
        return destAirport;
    }

    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }

    public void setDestAirport(Airport destAirport) {
        this.destAirport = destAirport;
    }

    public short getDistance() {
        return distance;
    }

    public void setDistance(short distance) {
        this.distance = distance;
    }

    public void setCrewTypes(CrewTypes crewTypes) {
        this.crewTypes = crewTypes;
    }

    public CrewTypes getCrewTypes() {
        return crewTypes;
    }
}
