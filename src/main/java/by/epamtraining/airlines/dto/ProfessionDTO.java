package by.epamtraining.airlines.dto;

import by.epamtraining.airlines.domain.Profession;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class ProfessionDTO {
    private int id;

    @Size(min = 2, max = 255)
    private String name;

    @Size(max = 255)
    @Nullable
    private String rank;

    @Nullable
    private BigDecimal hourRate;

    public ProfessionDTO() {
    }

    public ProfessionDTO(Profession profession) {
        id = profession.getId();
        name = profession.getName();
        rank = profession.getRank();
        hourRate = profession.getHourRate();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public String getRank() {
        return rank;
    }

    public void setRank(@Nullable String rank) {
        this.rank = rank;
    }

    @Nullable
    public BigDecimal getHourRate() {
        return hourRate;
    }

    public void setHourRate(@Nullable BigDecimal hourRate) {
        this.hourRate = hourRate;
    }
}
