package by.epamtraining.airlines.service.impl;

import by.epamtraining.airlines.domain.UserCredentials;
import by.epamtraining.airlines.domain.UserRole;
import by.epamtraining.airlines.repository.UserRepository;
import by.epamtraining.airlines.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserAuthService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        by.epamtraining.airlines.domain.User usr = userService.getByEmail(s);
        if (usr.isAccountActivated()) {
            UserCredentials credentials = usr.getCredentials().stream().filter(cred -> cred.isActive()).findAny().get();
            return new org.springframework.security.core.userdetails.User(usr.getEmail(),
                    credentials.getPwd(),
                    getAuthorities(s)
            );
        }
        return null;
    }

    private List<? extends GrantedAuthority> getAuthorities(String s) {
        List<SimpleGrantedAuthority> authList = new ArrayList<>();
        UserRole role = userService.getByEmail(s).getUserRole();
        SimpleGrantedAuthority auth = new SimpleGrantedAuthority("ROLE_" + role.name());
        authList.add(auth);
        return authList;
    }


}
