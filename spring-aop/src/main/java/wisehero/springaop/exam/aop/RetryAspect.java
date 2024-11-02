package wisehero.springaop.exam.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import lombok.extern.slf4j.Slf4j;
import wisehero.springaop.exam.annotation.Retry;

@Slf4j
@Aspect
public class RetryAspect {

	@Around("@annotation(retry)")
	public Object doRetry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {
		log.info("[retry] {}", joinPoint.getSignature(), retry);

		int maxRetry = retry.value();
		Exception exceptionHolder = null;

		for (int retryCount = 0; retryCount <= maxRetry; retryCount++) {
			try {
				log.info("[retry] try count={}/{}", retryCount, maxRetry);
			} catch (Exception e) {
				exceptionHolder = e;
			}
		}
		throw exceptionHolder;
	}
}
