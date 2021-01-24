package by.epamtraining.airlines.init;

import by.epamtraining.airlines.domain.Profession;
import by.epamtraining.airlines.service.ProfessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class InitProfessions {

    @Autowired
    ProfessionService professionService;

    public void addData() {
        Profession profession = new Profession();
        profession.setRank("middle");
        profession.setName("air hostess");
        profession.setHourRate(new BigDecimal(15));
        professionService.saveProfession(profession);

        profession = new Profession();
        profession.setRank("senior");
        profession.setName("air hostess");
        profession.setHourRate(new BigDecimal(20));
        professionService.saveProfession(profession);

        profession = new Profession();
        profession.setRank("senior");
        profession.setName("pilot");
        profession.setHourRate(new BigDecimal(40));
        professionService.saveProfession(profession);

        profession = new Profession();
        profession.setRank("middle");
        profession.setName("pilot");
        profession.setHourRate(new BigDecimal(30));
        professionService.saveProfession(profession);

        profession = new Profession();
        profession.setRank("middle");
        profession.setName("navigator");
        profession.setHourRate(new BigDecimal(30));
        professionService.saveProfession(profession);

        profession = new Profession();
        profession.setRank("middle");
        profession.setName("radioman");
        profession.setHourRate(new BigDecimal(30));
        professionService.saveProfession(profession);
    }

}
