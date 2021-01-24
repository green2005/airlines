package by.epamtraining.airlines.dto;

import by.epamtraining.airlines.domain.Profession;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProfessionDTOConverter implements Converter<ProfessionDTO, Profession> {
    @Override
    public Profession convert(ProfessionDTO professionDTO) {
        Profession profession = new Profession();
        profession.setName(professionDTO.getName());
        profession.setRank(professionDTO.getRank());
        profession.setHourRate(professionDTO.getHourRate());
        profession.setId(professionDTO.getId());
        return profession;
    }
}
