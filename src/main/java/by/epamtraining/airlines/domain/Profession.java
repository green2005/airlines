package by.epamtraining.airlines.domain;

import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Currency;

@Entity
public class Profession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    @Nullable
    private String rank;

    private BigDecimal hourRate;

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
}
