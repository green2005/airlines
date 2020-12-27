package by.epamtraining.airlines.domain;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Date;

@Entity
public class Flights {
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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "flightsPersonnel_id")
    FlightsPersonnel flightsPersonnel;

    private Date departureTime;

    private Date destTime;

    public String getFlightShortName() {
        return String.format("%s - %s", departureAirport.getShortName(), destAirport.getShortName());
    }

    public String getFlightFullName() {
        return String.format("%s - %s", departureAirport.getFullName(), destAirport.getFullName());
    }

    public Flights() {

    }

    public Flights(RegularFlightTemplate template) {

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
}
