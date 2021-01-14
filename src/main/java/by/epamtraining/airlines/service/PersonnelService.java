package by.epamtraining.airlines.service;

import by.epamtraining.airlines.domain.Personnel;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface PersonnelService {
    List<Personnel> getPersonnel();

    List<Personnel> getByProfession(String professionName, String rank);

    Page<Personnel> getPersonnel(Integer pageN, Integer pageSize, String sortField, Boolean sortAsc);

    Optional<Personnel> getById(int id);

    void deleteById(int id);

    void save(Personnel person);
}
