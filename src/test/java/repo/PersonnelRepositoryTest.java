package repo;

import by.epamtraining.airlines.domain.Personnel;
import by.epamtraining.airlines.repository.AirportRepository;
import by.epamtraining.airlines.repository.PersonnelRepository;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(classes = {repo.UserRepositoryTest.EmbeddedPostgresContextConfiguration.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PersonnelRepositoryTest {


    @Configuration
    @EntityScan(basePackageClasses = PersonnelRepository.class)
    @EnableJpaRepositories(basePackageClasses = AirportRepository.class)
    public static class EmbeddedPostgresContextConfiguration {

        @Bean
        @Primary
        public DataSource embeddedPG() throws IOException {
            return EmbeddedPostgres.start().getPostgresDatabase();
        }

    }

    @Autowired
    private PersonnelRepository personnelRepository;

    @Test
    public void testRepoPresent() {
        assertNotNull(personnelRepository);
    }

    @BeforeEach
    public void setUpDB() throws ParseException {
        Personnel personnel = new Personnel();
        personnel.setFirstName("Nick");
        personnel.setLastName("Vazovsky");
        SimpleDateFormat sd = new SimpleDateFormat("YYYY-MM-dd-HH:mm:ss");
        personnel.setBirthDate(sd.parse("2000-12-20-00:00:00"));
        personnelRepository.save(personnel);
    }

    @Test
    public void testPersonnelCreate() {
        List<Personnel> personnel = personnelRepository.getByLastName("Vazovsky");
        assertTrue(personnel.size() == 1);
    }

    @Test
    public void testUnique() throws ParseException {
        Personnel personnel = new Personnel();
        personnel.setFirstName("Nick");
        personnel.setLastName("Vazovsky");
        SimpleDateFormat sd = new SimpleDateFormat("YYYY-MM-dd-HH:mm:ss");
        personnel.setBirthDate(sd.parse("2000-12-20-00:00:00"));
        assertThrows(org.springframework.dao.DataIntegrityViolationException.class, () -> {
            personnelRepository.save(personnel);
        });
    }
}


