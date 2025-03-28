package wisehero.springadvanced.proxy.config.v3_proxyfacotry.advice;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import lombok.extern.slf4j.Slf4j;
import wisehero.springadvanced.proxy.trace.TraceStatus;
import wisehero.springadvanced.proxy.trace.logtrace.LogTrace;

@Slf4j
public class LogTraceAdvice implements MethodInterceptor {

	private final LogTrace logTrace;

	public LogTraceAdvice(LogTrace logTrace) {
		this.logTrace = logTrace;
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		TraceStatus status = null;

		try {
			Method method = invocation.getMethod();
			String message = method.getDeclaringClass().getSimpleName() + "." + method.getName() + "()";
			status = logTrace.begin(message);

			// 로직 호출
			Object result = invocation.proceed();
			logTrace.end(status);
			return result;
		} catch (Exception e) {
			logTrace.exception(status, e);
			throw e;
		}
	}
}
