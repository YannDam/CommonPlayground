package commonplayground;

import commonplayground.model.Session;
import commonplayground.model.SessionRepository;
import commonplayground.model.User;
import commonplayground.model.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application{

    public static void main(String[] args) {
        String ENV_PORT = System.getenv().get("PORT");
        String ENV_DYNO = System.getenv().get("DYNO");
        if(ENV_PORT != null && ENV_DYNO != null) {
            System.getProperties().put("server.port", ENV_PORT);
        }
        SpringApplication.run(Application.class);
    }

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Bean
    public CommandLineRunner demo(SessionRepository sessionRepository, UserRepository userRepository) {
        return (args) -> {
            sessionRepository.save(new Session("Card Game", "Card fun", "Doppelkopf", "Schlosspark", "22.11.2018", "12:00", 4, Long.parseLong("1")));
            sessionRepository.save(new Session("Raid", "Raid together", "CS", "WWW", "12.12.2018", "16:00", 42, Long.parseLong("1")));
            userRepository.save(new User("iBims", "123456789", "test@test.de"));

            // fetch all sessions
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (Session session : sessionRepository.findAll()) {
                log.info(session.toString());
            }
            log.info("");

            // fetch an individual session by ID
            sessionRepository.findById(1L)
                    .ifPresent(customer -> {
                        log.info("Customer found with findById(1L):");
                        log.info("--------------------------------");
                        log.info(customer.toString());
                        log.info("");
                    });
        };
    }

}
