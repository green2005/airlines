package repo;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import javax.sql.DataSource;

import by.epamtraining.airlines.domain.User;
import by.epamtraining.airlines.repository.UserRepository;
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

import com.opentable.db.postgres.embedded.EmbeddedPostgres;


    @ExtendWith(SpringExtension.class)
    @DataJpaTest
    @ContextConfiguration(classes = { UserRepositoryTest.EmbeddedPostgresContextConfiguration.class })
    @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
    public class UserRepositoryTest {

        private static final String EMAIL = "admin";

        @Configuration
        @EntityScan(basePackageClasses = User.class)
        @EnableJpaRepositories(basePackageClasses = UserRepository.class)
        public static class EmbeddedPostgresContextConfiguration {

            @Bean
            @Primary
            public DataSource embeddedPG() throws IOException {
                return EmbeddedPostgres.start().getPostgresDatabase();
            }

        }

        @Autowired
        private UserRepository repo;

        @Test
        public void testRepoPresent() {
            assertNotNull(repo);
        }

        @BeforeEach
        public void setUpDB() {
            User user = new User();

            user.setEmail(EMAIL);
            user.setName("fName");

            repo.save(user);
        }

        @Test
        public void testCreate_readByUserName() {
            // WHEN
            User findByEmail = repo.findByEmail(EMAIL);

            // THEN
            assertNotNull(findByEmail);
            assertEquals("fName", findByEmail.getName());
        }

    }
