package by.epamtraining.airlines.service.impl;

import by.epamtraining.airlines.domain.Profession;
import by.epamtraining.airlines.repository.ProfessionRepository;
import by.epamtraining.airlines.service.ProfessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessionServiceImpl implements ProfessionService {

    @Autowired
    ProfessionRepository professionRepository;

    @Override
    public List<Profession> getProfessions() {
        return professionRepository.findAll(Sort.by("name"));
    }

    @Override
    public Optional<Profession> getProfessionById(int id) {
        return professionRepository.findById(id);
    }

    @Override
    public void saveProfession(Profession profession) {
        professionRepository.save(profession);
    }

    @Override
    public void deleteProfession(int id) {
        professionRepository.deleteById(id);
    }

}
