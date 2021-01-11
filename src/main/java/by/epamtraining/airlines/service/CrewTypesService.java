package by.epamtraining.airlines.service;

import by.epamtraining.airlines.domain.CrewTypes;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CrewTypesService {
    public List<CrewTypes> getCrewTypes();
}
