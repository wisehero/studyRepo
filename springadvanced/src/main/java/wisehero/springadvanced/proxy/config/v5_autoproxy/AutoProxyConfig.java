package wisehero.springadvanced.proxy.config.v5_autoproxy;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import wisehero.springadvanced.proxy.config.AppV1Config;
import wisehero.springadvanced.proxy.config.AppV2Config;
import wisehero.springadvanced.proxy.config.v3_proxyfacotry.advice.LogTraceAdvice;
import wisehero.springadvanced.proxy.trace.logtrace.LogTrace;

@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class AutoProxyConfig {

	// @Bean
	// public Advisor advisor1(LogTrace logTrace) {
	// 	NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
	// 	pointcut.setMappedNames("request*", "order*", "save*");
	// 	LogTraceAdvice advice = new LogTraceAdvice(logTrace);
	// 	return new DefaultPointcutAdvisor(pointcut, advice);
	// }

	// @Bean
	// public Advisor advisor2(LogTrace logTrace) {
	// 	AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
	// 	pointcut.setExpression("execution(* wisehero.springadvanced.proxy.app..*(..))");
	// 	LogTraceAdvice advice = new LogTraceAdvice(logTrace);
	// 	return new DefaultPointcutAdvisor(pointcut, advice);
	// }

	@Bean
	public Advisor advisor3(LogTrace logTrace) {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(
			"execution(* hello.proxy.app..*(..)) && !execution(* wisehero.springadvanced.proxy.app..noLog(..))");
		LogTraceAdvice advice = new LogTraceAdvice(logTrace);
		return new DefaultPointcutAdvisor(pointcut, advice);
	}
}
