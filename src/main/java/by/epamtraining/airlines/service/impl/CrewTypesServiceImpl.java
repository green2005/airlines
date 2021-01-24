package by.epamtraining.airlines.service.impl;

import by.epamtraining.airlines.domain.CrewTypes;
import by.epamtraining.airlines.repository.CrewTypeRepository;
import by.epamtraining.airlines.service.CrewTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CrewTypesServiceImpl implements CrewTypesService {

    @Autowired
    CrewTypeRepository crewTypeRepository;

    @Override
    public List<CrewTypes> getCrewTypes() {
        return crewTypeRepository.findAll(Sort.by("name"));
    }

    @Override
    public void add(CrewTypes ct) {
        crewTypeRepository.save(ct);
    }

    @Override
    public CrewTypes findByName(String name) {
        return crewTypeRepository.findByName(name);
    }

    @Override
    public Optional<CrewTypes> findById(int id) {
        return crewTypeRepository.findById(id);
    }
}
