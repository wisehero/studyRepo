package wisehero.springaop.pointcut;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import wisehero.springaop.order.OrderService;

@Slf4j
@SpringBootTest
public class BeanTest {

	@Autowired
	OrderService orderService;

	@Test
	void success() {
		orderService.orderItem("itemA");
	}

	@Aspect
	static class BeanAspect {
		@Around("bean(orderService) || bean(*Repository)")
		public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
			log.info("[bean] {}", joinPoint.getSignature());
			return joinPoint.proceed();
		}
	}
}
