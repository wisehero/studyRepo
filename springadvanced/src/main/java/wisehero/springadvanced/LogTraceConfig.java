package wisehero.springadvanced;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import wisehero.springadvanced.trace.logtrace.LogTrace;
import wisehero.springadvanced.trace.logtrace.ThreadLocalLogTrace;

@Configuration
public class LogTraceConfig {

	@Bean
	public LogTrace logTrace() {
		// return new FieldLogTrace();
		return new ThreadLocalLogTrace();
	}
}
