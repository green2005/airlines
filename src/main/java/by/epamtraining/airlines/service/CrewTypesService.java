package by.epamtraining.airlines.service;

import by.epamtraining.airlines.domain.CrewTypes;

import java.util.List;
import java.util.Optional;


public interface CrewTypesService {
    List<CrewTypes> getCrewTypes();
    void add(CrewTypes ct);
    CrewTypes findByName(String name);
    Optional<CrewTypes> findById(int id);
}
