package by.epamtraining.airlines.repository;


import by.epamtraining.airlines.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    User findByName(String name);
}
