package by.epamtraining.airlines.domain;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "rank"})
})
public class Profession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min = 2, max = 255)
    private String name;

    @Size(max = 255)
    @Nullable
    private String rank;

    private BigDecimal hourRate;

    @OneToMany(mappedBy = "profession", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Personnel> personnelList;

    @ManyToMany(mappedBy = "professions", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CrewTypes> crewTypes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getHourRate() {
        return hourRate;
    }

    public void setHourRate(BigDecimal hourRate) {
        this.hourRate = hourRate;
    }

    @Nullable
    public String getRank() {
        return rank;
    }

    public void setRank(@Nullable String rank) {
        this.rank = rank;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
