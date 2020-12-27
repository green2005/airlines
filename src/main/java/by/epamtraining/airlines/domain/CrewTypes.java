package by.epamtraining.airlines.domain;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CrewTypes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "rlCrewProfessions",
            joinColumns = {@JoinColumn(name = "crew_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "profession_id", nullable = false)}
    )
    private List<Profession> professions = new ArrayList();

    public int getId() {
        return id;
    }

    public List<Profession> getProfessions() {
        return professions;
    }

    public void setProfessions(List<Profession> professions) {
        this.professions = professions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
