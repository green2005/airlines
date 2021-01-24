package by.epamtraining.airlines.init;

import by.epamtraining.airlines.domain.CrewTypes;
import by.epamtraining.airlines.service.CrewTypesService;
import by.epamtraining.airlines.service.ProfessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitCrewTypes {

    @Autowired
    CrewTypesService crewTypesService;
    @Autowired
    ProfessionService professionService;

    public void addData() {
        CrewTypes ct = new CrewTypes();
        ct.setName("full");
        ct.setDescription("full crew for flight");
        ct.addProfession(professionService.getProfession("pilot", "middle"));
        ct.addProfession(professionService.getProfession("pilot", "middle"));
        ct.addProfession(professionService.getProfession("navigator", "middle"));
        ct.addProfession(professionService.getProfession("air hostess", "middle"));
        crewTypesService.add(ct);

        ct = new CrewTypes();
        ct.setName("clipped");
        ct.setDescription("clipped crew");
        ct.addProfession(professionService.getProfession("pilot", "middle"));
        ct.addProfession(professionService.getProfession("navigator", "middle"));
        ct.addProfession(professionService.getProfession("air hostess", "middle"));
        crewTypesService.add(ct);
    }
}
