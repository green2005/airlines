package by.epamtraining.airlines.init;

import by.epamtraining.airlines.repository.UserRepository;
import by.epamtraining.airlines.service.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InitTestData implements InitializingBean {
    @Autowired
    PasswordEncoder encoder;


    @Autowired
    InitUsers initUsers;
    @Autowired
    InitAirports initAirports;
    @Autowired
    InitProfessions initProfessions;

    @Autowired
    InitPersonnel initPersonnel;

    @Autowired
    InitCrewTypes initCrewTypes;

    @Autowired
   InitFlights initFlights;

    @Override
    public void afterPropertiesSet() throws Exception {
        initUsers.addData();
        initAirports.addData();
        initProfessions.addData();
        initPersonnel.addData();
        initCrewTypes.addData();
        initFlights.addData();
    }
}
