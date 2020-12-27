package by.epamtraining.airlines.service.impl;

import by.epamtraining.airlines.domain.User;
import by.epamtraining.airlines.domain.UserCredentials;
import by.epamtraining.airlines.exceptions.UserNotFoundException;
import by.epamtraining.airlines.repository.UserRepository;
import by.epamtraining.airlines.service.EmailService;
import by.epamtraining.airlines.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

@Service
public class JPAUserServiceImpl implements UserService {

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
        UserCredentials credentials = user.getCredentials().stream().findAny().get();
        credentials.setActive(true);
        credentials.setPwd(encoder.encode(credentials.getPwd()));
        user = repository.save(user);
        emailService.sendRegistrationEmail(user);
    }

    @Override
    public void setUserPwd(User user, String pwd) {
        user.getCredentials().stream().forEach(cred -> cred.setActive(false));
        UserCredentials cred = new UserCredentials();
        cred.setActive(true);
        cred.setPwd(encoder.encode(pwd));
        user.getCredentials().add(cred);
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
            if ((usr == null) || (!code.equalsIgnoreCase(usr.getCRC()))) throw new UserNotFoundException();
            usr.setAccountActivated(true);
            repository.save(usr);
        } catch (Exception e) {
            Exception userException = new UserNotFoundException();
            userException.addSuppressed(e);
            throw userException;
        }
    }

    @Override
    public User getByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public User getByEmail(String email) {
        return repository.findByEmail(email);
    }
}
