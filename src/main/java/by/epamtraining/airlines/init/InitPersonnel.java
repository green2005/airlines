package by.epamtraining.airlines.init;

import by.epamtraining.airlines.domain.Personnel;
import by.epamtraining.airlines.domain.Sex;
import by.epamtraining.airlines.service.PersonnelService;
import by.epamtraining.airlines.service.ProfessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

@Component
public class InitPersonnel {

    @Autowired
    PersonnelService personnelService;

    @Autowired
    ProfessionService professionService;

    public void addData() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);

        Personnel personnel = new Personnel();
        personnel.setGender(Sex.MALE);
        personnel.setFirstName("Mike");
        personnel.setLastName("Vazovsky");
        personnel.setBirthDate(formatter.parse("20.12.1985"));
        personnel.setProfession(professionService.getProfession("navigator", "middle"));
        personnelService.save(personnel);

        personnel = new Personnel();
        personnel.setGender(Sex.MALE);
        personnel.setFirstName("Nick");
        personnel.setLastName("Vazovsky");
        personnel.setBirthDate(formatter.parse("10.02.1980"));
        personnel.setProfession(professionService.getProfession("navigator", "middle"));
        personnelService.save(personnel);

        personnel = new Personnel();
        personnel.setGender(Sex.MALE);
        personnel.setFirstName("Petr");
        personnel.setLastName("Johnson");
        personnel.setBirthDate(formatter.parse("01.01.1979"));
        personnel.setProfession(professionService.getProfession("pilot", "middle"));
        personnelService.save(personnel);

        personnel = new Personnel();
        personnel.setGender(Sex.FEMALE);
        personnel.setFirstName("Keyth");
        personnel.setLastName("Klinton");
        personnel.setBirthDate(formatter.parse("09.04.1979"));
        personnel.setProfession(professionService.getProfession("pilot", "middle"));
        personnelService.save(personnel);

        personnel = new Personnel();
        personnel.setGender(Sex.MALE);
        personnel.setFirstName("Alex");
        personnel.setLastName("Jezoff");
        personnel.setBirthDate(formatter.parse("02.04.1995"));
        personnel.setProfession(professionService.getProfession("pilot", "middle"));
        personnelService.save(personnel);

        personnel = new Personnel();
        personnel.setGender(Sex.MALE);
        personnel.setFirstName("John");
        personnel.setLastName("Smith");
        personnel.setBirthDate(formatter.parse("09.04.1993"));
        personnel.setProfession(professionService.getProfession("pilot", "middle"));
        personnelService.save(personnel);

        personnel = new Personnel();
        personnel.setGender(Sex.FEMALE);
        personnel.setFirstName("Jess");
        personnel.setLastName("Boyden");
        personnel.setBirthDate(formatter.parse("22.09.1989"));
        personnel.setProfession(professionService.getProfession("air hostess", "middle"));
        personnelService.save(personnel);


        personnel = new Personnel();
        personnel.setGender(Sex.FEMALE);
        personnel.setFirstName("Emily");
        personnel.setLastName("Spark");
        personnel.setBirthDate(formatter.parse("20.10.1994"));
        personnel.setProfession(professionService.getProfession("air hostess", "middle"));
        personnelService.save(personnel);

        personnel = new Personnel();
        personnel.setGender(Sex.FEMALE);
        personnel.setFirstName("Sandra");
        personnel.setLastName("Nasik");
        personnel.setBirthDate(formatter.parse("22.09.1992"));
        personnel.setProfession(professionService.getProfession("air hostess", "middle"));
        personnelService.save(personnel);

        personnel = new Personnel();
        personnel.setGender(Sex.MALE);
        personnel.setFirstName("Alehandro");
        personnel.setLastName("Pereiro");
        personnel.setBirthDate(formatter.parse("21.07.1990"));
        personnel.setProfession(professionService.getProfession("radioman", "middle"));
        personnelService.save(personnel);

        personnel = new Personnel();
        personnel.setGender(Sex.MALE);
        personnel.setFirstName("John");
        personnel.setLastName("Malkovich");
        personnel.setBirthDate(formatter.parse("14.04.1985"));
        personnel.setProfession(professionService.getProfession("radioman", "middle"));
        personnelService.save(personnel);

    }
}
