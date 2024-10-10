package wisehero.springadvanced;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import wisehero.springadvanced.proxy.config.AppV1Config;

@SpringBootApplication(scanBasePackages = "wisehero.springadvanced.proxy")
@Import(AppV1Config.class)
public class SpringadvancedApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringadvancedApplication.class, args);
	}

}
