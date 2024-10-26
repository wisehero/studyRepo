package wisehero.springadvanced.proxy.advisor;

import java.lang.reflect.Method;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

import lombok.extern.slf4j.Slf4j;
import wisehero.springadvanced.proxy.common.ServiceInterface;
import wisehero.springadvanced.proxy.common.advice.TimeAdvice;
import wisehero.springadvanced.proxy.common.service.ServiceImpl;

@Slf4j
public class AdvisorTest {

	@Test
	void advisorTest1() {
		ServiceInterface target = new ServiceImpl();
		ProxyFactory proxyFactory = new ProxyFactory(target);

		// 어드바이스를 넣은 어드바이저 생성
		DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice());

		// 프록시에 어드바이저 추가
		proxyFactory.addAdvisor(advisor);
		ServiceInterface proxy = (ServiceInterface)proxyFactory.getProxy();

		proxy.save();
		proxy.find();
	}

	@Test
	@DisplayName("직접 만든 포인트컷")
	void advisorTest2() {
		ServiceImpl target = new ServiceImpl();
		ProxyFactory proxyFactory = new ProxyFactory(target);
		DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(new MyPointcut(), new TimeAdvice());
		proxyFactory.addAdvisor(advisor);
		ServiceInterface proxy = (ServiceInterface)proxyFactory.getProxy();

		proxy.save();
		proxy.find();
	}

	static class MyPointcut implements Pointcut {

		@Override
		public ClassFilter getClassFilter() {
			return ClassFilter.TRUE;
		}

		@Override
		public MethodMatcher getMethodMatcher() {
			return new MyMethodMatcher();
		}
	}

	static class MyMethodMatcher implements MethodMatcher {
		private String matchName = "save";

		@Override
		public boolean matches(Method method, Class<?> targetClass) {
			// save라는 이름이 넘어오면 true를 반환하도록 설정
			boolean result = method.getName().equals(matchName);
			log.info("포인트컷 호출 method={} targetClass={}", method.getName(), targetClass);
			log.info("포인트컷 결과 result={}", result);
			return result;
		}

		// 아래의 메서드가 true이면 아래의 matches가 사용된다.
		@Override
		public boolean isRuntime() {
			return false;
		}

		@Override
		public boolean matches(Method method, Class<?> targetClass, Object... args) {
			throw new UnsupportedOperationException();
		}
	}
}
