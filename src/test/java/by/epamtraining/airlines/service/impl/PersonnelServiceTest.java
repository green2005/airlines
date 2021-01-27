package by.epamtraining.airlines.service.impl;

import by.epamtraining.airlines.domain.Personnel;
import by.epamtraining.airlines.domain.Profession;
import by.epamtraining.airlines.domain.Sex;
import by.epamtraining.airlines.service.PersonnelService;
import by.epamtraining.airlines.service.ProfessionService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PersonnelServiceTest {
    private static String lastName = UUID.randomUUID().toString();

    private static String professionName = UUID.randomUUID().toString();
    private static String professionRank = UUID.randomUUID().toString();

    @Autowired
    private PersonnelService personnelService;

    @Autowired
    private ProfessionService professionService;

    @Test
    public void contextLoads() throws Exception {
        assertThat(personnelService).isNotNull();
        assertThat(professionService).isNotNull();
    }

    @BeforeEach
    private void insertTestRecord() throws ParseException {

        Profession profession = professionService.getProfession(professionName, professionRank);
        if (profession == null) {
            profession = new Profession();
            profession.setName(professionName);
            profession.setRank(professionRank);
            professionService.saveProfession(profession);
        }
        List<Personnel> personnel = personnelService.getByLastName(lastName);
        if (personnel.isEmpty()) {
            insertPerson();
        }
    }

    @Test
    public void getByLastName() throws Exception {
        List<Personnel> personnelList = personnelService.getByLastName(lastName);
        assertThat(personnelList.size() == 1);
    }

    @Test
    public void getById() throws Exception {
        List<Personnel> personnelList = personnelService.getByLastName(lastName);
        Personnel personnel = personnelList.get(0);
        Personnel personnel1 = personnelService.getById(personnel.getId()).get();
        assertThat(personnel1 == personnel);
    }

    @Test
    public void getByProfession() {
        Profession profession = professionService.getProfession(professionName, professionRank);
        assertThat(profession != null);
        List<Personnel> personnelList = personnelService.getByProfession(profession.getName(), profession.getRank());
        assertThat(personnelList.size() == 1);
        assertThat(personnelList.get(0).getLastName().equals(lastName));
    }

    @Test
    public void getAll() {
        Page<Personnel> persons = personnelService.getPersonnel(1, 1, "firstName", true);
        assertThat(persons.getContent().size() == 1);
    }

    @Test
    public void delete() throws ParseException {
        List<Personnel> personnel = personnelService.getByLastName(lastName);
        assertThat(personnel.size() == 1);
        personnelService.deleteById(personnel.get(0).getId());
        personnel = personnelService.getByLastName(lastName);
        assertThat(personnel.size() == 0);
    }

    private void insertPerson() throws ParseException {
        Personnel personnel = new Personnel();
        personnel.setLastName(lastName);
        personnel.setFirstName(lastName);
        personnel.setGender(Sex.MALE);
        Profession profession = professionService.getProfession(professionName, professionRank);
        personnel.setProfession(profession);
        SimpleDateFormat sd = new SimpleDateFormat("YYYY-MM-dd-HH:mm:ss");
        personnel.setBirthDate(sd.parse("2000-12-20-00:00:00"));
        personnelService.save(personnel);
    }
}
