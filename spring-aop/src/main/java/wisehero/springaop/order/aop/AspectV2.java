package wisehero.springaop.order.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
public class AspectV2 {

	/**
	 * 포인트컷 시그니쳐 작성법
	 *
	 * 1. 반환 타입은 void
	 * 2. 구현부는 비워둔다.
	 * 3. 다른 애스펙트에서 참고하려면 public 그렇지 않으면 private
	 */
	@Pointcut("execution(* wisehero.springaop.order..*(..))")
	public void allOrder() {
	} // pointcut signature

	@Around("allOrder()")
	public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
		log.info("[log] {}", joinPoint.getSignature());
		return joinPoint.proceed();
	}
}
