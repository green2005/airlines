package by.epamtraining.airlines.service;

import by.epamtraining.airlines.domain.Profession;

import java.util.List;
import java.util.Optional;

public interface ProfessionService {
    List<Profession> getProfessions();

    Optional<Profession> getProfessionById(int id);

    void saveProfession(Profession profession);

    void deleteProfession(int id);
}