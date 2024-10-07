package wisehero.springadvanced;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import wisehero.springadvanced.trace.logtrace.FieldLogTrace;
import wisehero.springadvanced.trace.logtrace.LogTrace;

@Configuration
public class LogTraceConfig {

	@Bean
	public LogTrace logTrace() {
		return new FieldLogTrace();
	}
}
