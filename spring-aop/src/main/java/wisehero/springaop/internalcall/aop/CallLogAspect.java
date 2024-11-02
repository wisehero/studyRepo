package wisehero.springaop.internalcall.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
public class CallLogAspect {

	@Before("execution(* wisehero.springaop.internalcall..*.*(..))")
	public void doLog(JoinPoint joinPoint) {
		log.info("aop={}", joinPoint.getSignature());
	}
}
