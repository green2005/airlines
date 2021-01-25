package by.epamtraining.airlines.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableScheduling
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/", "/index", "/index/{n}",
                        "/home", "/register", "/register/**", "/activate/**",
                        "/webjars/**", "/images/**", "/test", "/test/").
                permitAll()
                .anyRequest().authenticated().and().
                formLogin().
                loginPage("/login").
                permitAll().
                defaultSuccessUrl("/").
                and().
                logout().
                and().
                rememberMe().key("uniqueAndSecret");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().
                dataSource(dataSource).
                passwordEncoder(encoder()).
                usersByUsernameQuery("select usr.email as username, cred.pwd as password, usr.account_activated as active\n" +
                        "from user_table usr\n" +
                        "inner join user_credentials cred on cred.user_id =  usr.id and cred.is_active = true\n" +
                        "where usr.email=?");
        super.configure(auth);
    }

    private DaoAuthenticationProvider provider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(encoder());
        return provider;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}
