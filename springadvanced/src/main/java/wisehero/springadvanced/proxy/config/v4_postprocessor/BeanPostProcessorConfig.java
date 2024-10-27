package wisehero.springadvanced.proxy.config.v4_postprocessor;

import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import lombok.extern.slf4j.Slf4j;
import wisehero.springadvanced.proxy.config.AppV1Config;
import wisehero.springadvanced.proxy.config.AppV2Config;
import wisehero.springadvanced.proxy.config.v3_proxyfacotry.advice.LogTraceAdvice;
import wisehero.springadvanced.proxy.config.v4_postprocessor.postprocessor.PackageLogTraceProxyPostProcessor;
import wisehero.springadvanced.proxy.trace.logtrace.LogTrace;

@Slf4j
@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class BeanPostProcessorConfig {

	@Bean
	public PackageLogTraceProxyPostProcessor logTraceProxyPostProcessor(LogTrace logTrace) {
		return new PackageLogTraceProxyPostProcessor("wisehero.springadvanced.proxy.app", getAdvisor(logTrace));
	}

	private Advisor getAdvisor(LogTrace logTrace) {
		//pointcut
		NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
		pointcut.setMappedNames("request*", "order*", "save*");
		//advice
		LogTraceAdvice advice = new LogTraceAdvice(logTrace);
		//advisor = pointcut + advice
		return new DefaultPointcutAdvisor(pointcut, advice);
	}
}
