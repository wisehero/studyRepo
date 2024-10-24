package wisehero.springadvanced.proxy.advisor;

import org.junit.jupiter.api.Test;
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
}
