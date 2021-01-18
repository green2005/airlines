package by.epamtraining.airlines.service;

import by.epamtraining.airlines.domain.Personnel;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PersonnelService {
    List<Personnel> getPersonnel();

    List<Personnel> getByProfession(String professionName, String rank);

    Page<Personnel> getPersonnel(Integer pageN, Integer pageSize, String sortField, Boolean sortAsc);

    Optional<Personnel> getById(int id);

    void deleteById(int id);

    void save(Personnel person);

    List<Personnel> getByLastName(String lastName);

    List<Personnel> getBusyPersonnel(List<Integer> list, Integer id, Date d1, Date d2);//List<Personnel> personnelList);
}
