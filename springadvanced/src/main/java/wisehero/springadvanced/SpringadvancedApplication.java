package wisehero.springadvanced;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import wisehero.springadvanced.proxy.config.AppV1Config;
import wisehero.springadvanced.proxy.config.AppV2Config;

@SpringBootApplication(scanBasePackages = "wisehero.springadvanced.proxy.app.v2")
@Import({AppV1Config.class, AppV2Config.class})
public class SpringadvancedApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringadvancedApplication.class, args);
	}

}
