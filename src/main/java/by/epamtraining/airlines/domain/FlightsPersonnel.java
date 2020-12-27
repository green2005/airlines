package by.epamtraining.airlines.domain;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;

@Entity
public class FlightsPersonnel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "flightsPersonnel")
    Flights flight;

    @Nullable
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "crewTypeId", nullable = true)
    private CrewTypes crewTypes;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "rlFlightsPersonnel",
            joinColumns = @JoinColumn(name = "flightId"),
            inverseJoinColumns = @JoinColumn(name = "personnelId")
    )

    private List<Personnel> flightPersonnel = new ArrayList();

    @PrePersist
    @PreUpdate
    private void checkDataIsValid() {
        //Todo - add check: one person should not be present at the same time in several flights

        if (!checkCrewIsValid()) {
            throw new ValidationException("Crew data is not valid");
        }
    }

    private boolean checkCrewIsValid() {
        if (crewTypes == null) {
            return true;
        }
        List<String> professionListNeededForFlight = new ArrayList<>();
        crewTypes.getProfessions().forEach(profession -> professionListNeededForFlight.add(profession.getName()));
        flightPersonnel.forEach(person -> {
            professionListNeededForFlight.remove(person.profession.getName());
        });
        return professionListNeededForFlight.isEmpty();
    }


    FlightsPersonnel() {

    }

    FlightsPersonnel(CrewTypes crewTypes) {
        this.crewTypes = crewTypes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCrewTypes(@Nullable CrewTypes crewTypes) {
        this.crewTypes = crewTypes;
    }

    public List<Personnel> getFlightPersonnel() {
        return flightPersonnel;
    }

    public void setFlightPersonnel(List<Personnel> flightPersonnel) {
        this.flightPersonnel = flightPersonnel;
    }
}
