package wisehero.springadvanced;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import wisehero.springadvanced.proxy.config.v3_proxyfacotry.ProxyFactoryConfigV1;
import wisehero.springadvanced.proxy.trace.logtrace.LogTrace;
import wisehero.springadvanced.proxy.trace.logtrace.ThreadLocalLogTrace;

@Import(ProxyFactoryConfigV1.class)
@SpringBootApplication(scanBasePackages = "wisehero.springadvanced.proxy.app.v1")
public class SpringadvancedApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringadvancedApplication.class, args);
	}

	@Bean
	public LogTrace logTrace() {
		return new ThreadLocalLogTrace();
	}

}
