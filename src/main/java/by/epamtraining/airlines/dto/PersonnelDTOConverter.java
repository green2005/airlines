package by.epamtraining.airlines.dto;

import by.epamtraining.airlines.domain.Personnel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PersonnelDTOConverter implements Converter<PersonnelDTO, Personnel> {
    @Override
    public Personnel convert(PersonnelDTO personnelDTO) {
        Personnel personnel = new Personnel();
        personnel.setId(personnelDTO.getId());
        personnel.setProfession(personnelDTO.getProfession());
        personnel.setFirstName(personnelDTO.getFirstName());
        personnel.setLastName(personnelDTO.getLastName());
        personnel.setBirthDate(personnelDTO.getBirthDate());
        personnel.setGender(personnelDTO.getGender());
        return personnel;
    }
}
