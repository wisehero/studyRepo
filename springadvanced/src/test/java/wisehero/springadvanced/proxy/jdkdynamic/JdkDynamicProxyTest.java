package wisehero.springadvanced.proxy.jdkdynamic;

import java.lang.reflect.Proxy;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import wisehero.springadvanced.proxy.jdkdynamic.code.AImpl;
import wisehero.springadvanced.proxy.jdkdynamic.code.AInterface;
import wisehero.springadvanced.proxy.jdkdynamic.code.BImpl;
import wisehero.springadvanced.proxy.jdkdynamic.code.BInterface;
import wisehero.springadvanced.proxy.jdkdynamic.code.TimeInvocationHandler;

@Slf4j
public class JdkDynamicProxyTest {

	@Test
	void dynamicA() {
		// TimeInvocationHandler.invoke()
		// AImpl.call()
		AInterface target = new AImpl();
		TimeInvocationHandler handler = new TimeInvocationHandler(target);
		AInterface proxy =
			(AInterface)Proxy.newProxyInstance(
				AInterface.class.getClassLoader(),
				new Class[] {AInterface.class},
				handler);

		proxy.call();
		log.info("targetClass={}", target.getClass());
		log.info("proxyClass={}", proxy.getClass());
	}

	@Test
	void dynamicB() {
		// TimeInvocationHandler.invoke()
		// AImpl.call()
		BInterface target = new BImpl();
		TimeInvocationHandler handler = new TimeInvocationHandler(target);
		BInterface proxy = (BInterface)Proxy.newProxyInstance(
			BInterface.class.getClassLoader(),
			new Class[] {BInterface.class},
			handler);

		proxy.call();
		log.info("targetClass={}", target.getClass());
		log.info("proxyClass={}", proxy.getClass());
	}
}
