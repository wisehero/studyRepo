package wisehero.practicaltesting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PracticalTestingApplication {

	public static void main(String[] args) {
		SpringApplication.run(PracticalTestingApplication.class, args);
	}

}
