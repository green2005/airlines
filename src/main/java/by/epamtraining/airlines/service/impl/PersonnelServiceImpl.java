package by.epamtraining.airlines.service.impl;

import by.epamtraining.airlines.domain.Personnel;
import by.epamtraining.airlines.repository.PersonnelRepository;
import by.epamtraining.airlines.service.PersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonnelServiceImpl implements PersonnelService {

    @Autowired
    PersonnelRepository personnelRepository;

    @Override
    public List<Personnel> getPersonnel() {
        return personnelRepository.findAll(Sort.by("firstName"));
    }

    @Override
    public Page<Personnel> getPersonnel(Integer pageN, Integer pageSize, String sortField, Boolean sortAsc) {
        Sort sort;
        if (sortAsc) {
            sort = Sort.by(sortField).ascending();
        } else {
            sort = Sort.by(sortField).descending();
        }
        Pageable pageable = PageRequest.of(pageN - 1, pageSize, sort);
        return personnelRepository.findAll(pageable);
    }

    @Override
    public Optional<Personnel> getById(int id) {
        return personnelRepository.findById(id);
    }

    @Override
    public void deleteById(int id) {
        personnelRepository.deleteById(id);
    }

    @Override
    public void save(Personnel person) {
        personnelRepository.save(person);
    }
}
