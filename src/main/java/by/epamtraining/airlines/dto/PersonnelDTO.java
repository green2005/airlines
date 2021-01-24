package by.epamtraining.airlines.dto;

import by.epamtraining.airlines.domain.Personnel;
import by.epamtraining.airlines.domain.Profession;
import by.epamtraining.airlines.domain.Sex;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class PersonnelDTO {

    public PersonnelDTO() {
    }


    public PersonnelDTO(Personnel personnel) {
        id = personnel.getId();
        profession = personnel.getProfession();
        firstName = personnel.getFirstName();
        lastName = personnel.getLastName();
        birthDate = personnel.getBirthDate();
        gender = personnel.getGender();
    }

    private int id;

    @NotNull
    @Size(min = 3, max = 50)
    private String firstName;

    @NotNull
    @Size(min = 3, max = 50)
    private String lastName;

    private Profession profession;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    @NotNull
    private Sex gender;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Sex getGender() {
        return gender;
    }

    public void setGender(Sex gender) {
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
