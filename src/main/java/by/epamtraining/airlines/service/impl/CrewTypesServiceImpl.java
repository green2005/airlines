package by.epamtraining.airlines.service.impl;

import by.epamtraining.airlines.domain.CrewTypes;
import by.epamtraining.airlines.repository.CrewTypeRepository;
import by.epamtraining.airlines.service.CrewTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CrewTypesServiceImpl implements CrewTypesService {

    @Autowired
    CrewTypeRepository crewTypeRepository;

    @Override
    public List<CrewTypes> getCrewTypes() {
        return crewTypeRepository.findAll(Sort.by("name"));
    }
}
