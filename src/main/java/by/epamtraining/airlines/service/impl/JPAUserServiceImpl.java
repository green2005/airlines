package by.epamtraining.airlines.service.impl;

import by.epamtraining.airlines.domain.User;
import by.epamtraining.airlines.domain.UserCredentials;
import by.epamtraining.airlines.domain.UserRole;
import by.epamtraining.airlines.exceptions.DomainNotFoundException;
import by.epamtraining.airlines.repository.UserRepository;
import by.epamtraining.airlines.service.EmailService;
import by.epamtraining.airlines.service.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JPAUserServiceImpl implements UserService, InitializingBean{

    @Autowired
    UserRepository repository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    EmailService emailService;

    @Override
    public List<User> getUserList() {
        return repository.findAll(Sort.by("name"));
    }

    @Override
    public void addUser(User user) {
        UserCredentials credentials = user.getCredentialsSet().stream().findAny().get();
        credentials.setActive(true);
        credentials.setPwd(encoder.encode(credentials.getPwd()));
        user = repository.save(user);
        emailService.sendRegistrationEmail(user);
    }

    @Override
    public void setUserPwd(User user, String pwd) {
        user.getCredentialsSet().stream().forEach(cred -> cred.setActive(false));
        UserCredentials cred = new UserCredentials();
        cred.setActive(true);
        cred.setPwd(encoder.encode(pwd));
        user.getCredentialsSet().add(cred);
        cred.setUser(user);
        repository.save(user);
    }

    @Override
    public void updateUser(User user) {
        repository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        repository.delete(user);
    }

    @Override
    public void activateUser(String userId, String code) throws Exception {
        try {
            User usr = repository.getOne(Integer.parseInt(userId));
            if ((usr == null) || (!code.equalsIgnoreCase(usr.getCRC()))) throw new DomainNotFoundException();
            usr.setAccountActivated(true);
            repository.save(usr);
        } catch (Exception e) {
            Exception userException = new DomainNotFoundException();
            userException.addSuppressed(e);
            throw userException;
        }
    }

    @Override
    public void resetPassword(User user) throws Exception {
        user.getCredentialsSet().stream().forEach(item -> item.setActive(false));
        repository.save(user);
        emailService.sendRestorePwdEmail(user);
    }

    @Override
    public User getByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public User getByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Optional<User> getById(Integer id) {
        return repository.findById(id);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        addData();
    }

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
        repository.save(usr);

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
        repository.save(usr);

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
        repository.save(usr);

    }
}
