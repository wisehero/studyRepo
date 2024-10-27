package wisehero.springadvanced.proxy.proxyfactory;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

import wisehero.springadvanced.proxy.common.ServiceInterface;
import wisehero.springadvanced.proxy.common.advice.TimeAdvice;
import wisehero.springadvanced.proxy.common.service.ConcreteService;
import wisehero.springadvanced.proxy.common.service.ServiceImpl;

public class ProxyFactoryTest {

	private static final Logger log = LoggerFactory.getLogger(ProxyFactoryTest.class);

	@Test
	@DisplayName("인터페이스가 있으면 JDK 동적 프록시 사용")
	void interfaceProxy() {
		ServiceInterface target = new ServiceImpl();
		ProxyFactory proxyFactory = new ProxyFactory(target);
		proxyFactory.addAdvice(new TimeAdvice());

		ServiceInterface proxy = (ServiceInterface)proxyFactory.getProxy();
		log.info("targetClass={}", target.getClass());
		log.info("proxyClass={}", proxy.getClass());

		proxy.save();

		assertThat(AopUtils.isAopProxy(proxy)).isTrue();
		assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue();
		assertThat(AopUtils.isCglibProxy(proxy)).isFalse();
	}

	@Test
	@DisplayName("구체 클래스만 있으면 JDK 동적 프록시 사용")
	void concreteProxy() {
		ConcreteService target = new ConcreteService();
		ProxyFactory proxyFactory = new ProxyFactory(target);
		proxyFactory.addAdvice(new TimeAdvice());

		ConcreteService proxy = (ConcreteService)proxyFactory.getProxy();
		log.info("targetClass={}", target.getClass());
		log.info("proxyClass={}", proxy.getClass());

		proxy.call();

		assertThat(AopUtils.isAopProxy(proxy)).isTrue();
		assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
		assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
	}

	@Test
	@DisplayName("ProxyTargetClass 설정을 true로 하면 인터페이스도 CGLIB 프록시 사용")
	void proxyTargetClass() {
		ServiceInterface target = new ServiceImpl();
		ProxyFactory proxyFactory = new ProxyFactory(target);
		proxyFactory.setProxyTargetClass(true);
		proxyFactory.addAdvice(new TimeAdvice());

		ServiceInterface proxy = (ServiceInterface)proxyFactory.getProxy();
		log.info("targetClass={}", target.getClass());
		log.info("proxyClass={}", proxy.getClass());

		proxy.save();

		assertThat(AopUtils.isAopProxy(proxy)).isTrue();
		assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
		assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
	}
}
