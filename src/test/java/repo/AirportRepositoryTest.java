package repo;

import by.epamtraining.airlines.domain.Airport;
import by.epamtraining.airlines.domain.User;
import by.epamtraining.airlines.repository.AirportRepository;
import by.epamtraining.airlines.repository.UserRepository;
import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(classes = {repo.UserRepositoryTest.EmbeddedPostgresContextConfiguration.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AirportRepositoryTest {


    @Configuration
    @EntityScan(basePackageClasses = AirportRepository.class)
    @EnableJpaRepositories(basePackageClasses = AirportRepository.class)
    public static class EmbeddedPostgresContextConfiguration {

        @Bean
        @Primary
        public DataSource embeddedPG() throws IOException {
            return EmbeddedPostgres.start().getPostgresDatabase();
        }

    }

    @Autowired
    private AirportRepository airportRepository;

    @Test
    public void testRepoPresent() {
        assertNotNull(airportRepository);
    }

    @BeforeEach
    public void setUpDB() {
        Airport airport = new Airport();
        airport.setCountry("Belarus");
        airport.setCity("Minsk");
        airport.setShortName("MSQ");
        airport.setFullName("Minsk national airport");
        airportRepository.save(airport);
    }

    @Test
    public void testAirportCreate() {
        Airport minskAirport = airportRepository.getByShortName("MSQ");
        assertNotNull(minskAirport);
        assertEquals("Minsk", minskAirport.getCity());
    }

    @Test
    public void testUnique(){
        Airport minskAirport = airportRepository.getByShortName("MSQ");
        Airport airport = new Airport();
        airport.setShortName(minskAirport.getShortName());
        airport.setFullName("Blabla airport");
        airport.setCity("Blabla");
        airport.setCountry("Blabla country");
        assertThrows(org.springframework.dao.DataIntegrityViolationException.class, () -> {
            airportRepository.save(airport);
        });
    }
}


