package by.epamtraining.airlines.dto;

import by.epamtraining.airlines.domain.User;
import by.epamtraining.airlines.domain.UserCredentials;
import by.epamtraining.airlines.domain.UserRole;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserDTOConverter implements Converter<UserRegistrationDTO, User> {

    @Override
    public User convert(UserRegistrationDTO source) {
        User user = new User();
        user.setName(source.getName());
        user.setEmail(source.getEmail());
        user.setUserRole(UserRole.USER);
        UserCredentials creds = new UserCredentials();
        creds.setPwd(source.getPassword());
        creds.setActive(true);
        creds.setUser(user);
        Set<UserCredentials> credentialsSet = new HashSet<>();
        credentialsSet.add(creds);
        user.setCredentialsSet(credentialsSet);
        return user;
    }
}
