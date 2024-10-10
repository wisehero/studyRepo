package wisehero.springadvanced;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import wisehero.springadvanced.proxy.config.AppV1Config;
import wisehero.springadvanced.proxy.config.AppV2Config;

@SpringBootApplication(scanBasePackages = "wisehero.springadvanced.proxy.app.v3")
public class SpringadvancedApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringadvancedApplication.class, args);
	}

}
