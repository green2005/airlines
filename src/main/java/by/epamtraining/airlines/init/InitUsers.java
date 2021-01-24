package by.epamtraining.airlines.init;

import by.epamtraining.airlines.domain.User;
import by.epamtraining.airlines.domain.UserCredentials;
import by.epamtraining.airlines.domain.UserRole;
import by.epamtraining.airlines.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitUsers {

    @Autowired
    UserRepository userRepository;

    public void addData() {

        User usr = new User();
        usr.setName("admin");
        usr.setEmail("admin");
        usr.setUserRole(UserRole.ADMIN);
        UserCredentials credentials = new UserCredentials();
        credentials.setUser(usr);
        credentials.setPwd("$2a$10$HMchPRNB2LjPsKNfNjGBWucn43bDVtPRZlLSBjsYAh9n3t2YwFHq6"); //crypted "admin" word
        credentials.setActive(true);
        usr.addCredentials(credentials);
        usr.setAccountActivated(true);
        userRepository.save(usr);

        usr = new User();
        usr.setName("user");
        usr.setEmail("user");
        usr.setUserRole(UserRole.USER);
        credentials = new UserCredentials();
        credentials.setUser(usr);
        credentials.setPwd("$2a$10$73ZQLjD4c46/L33HLreGmeH1b6n7wmTzSuI4sCMRwvE9QxmWDamJO"); //crypted "user" word
        credentials.setActive(true);
        usr.addCredentials(credentials);
        usr.setAccountActivated(true);
        userRepository.save(usr);

        usr = new User();
        usr.setName("dispatcher");
        usr.setEmail("dispatcher");
        usr.setUserRole(UserRole.DISPATCHER);
        credentials = new UserCredentials();
        credentials.setUser(usr);
        credentials.setPwd("$2a$10$jGnBVi/uF2zQOOcq4Z96yetr9fH6RcwcI3FXUDNmEYQkiOxsOYzUK"); //crypted "dispatcher" word
        credentials.setActive(true);
        usr.addCredentials(credentials);
        usr.setAccountActivated(true);
        userRepository.save(usr);

    }
}
