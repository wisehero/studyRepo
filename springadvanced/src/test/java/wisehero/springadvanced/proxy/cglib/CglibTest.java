package wisehero.springadvanced.proxy.cglib;

import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

import lombok.extern.slf4j.Slf4j;
import wisehero.springadvanced.proxy.cglib.code.TimeMethodInterceptor;
import wisehero.springadvanced.proxy.common.service.ConcreteService;

@Slf4j
public class CglibTest {

	@Test
	void cglib() {
		ConcreteService target = new ConcreteService();

		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(ConcreteService.class);
		enhancer.setCallback(new TimeMethodInterceptor(target));
		ConcreteService proxy = (ConcreteService)enhancer.create();

		log.info("targetClass={}", target.getClass());
		log.info("proxyClass={}", proxy.getClass());

		proxy.call();
	}
}
