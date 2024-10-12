package wisehero.springadvanced.proxy;

import org.junit.jupiter.api.Test;

import wisehero.springadvanced.proxy.code.CacheProxy;
import wisehero.springadvanced.proxy.code.ProxyPatternClient;
import wisehero.springadvanced.proxy.code.Subject;

public class ProxyPatternTest {

	@Test
	void noProxyTest() {
		RealSubject realSubject = new RealSubject(); // 실제 객체를 생성해서 사용하므로 프록시가 아니다.
		ProxyPatternClient client = new ProxyPatternClient(realSubject);
		client.execute();
		client.execute();
		client.execute();
	}

	@Test
	void cacheProxyTest() {
		Subject realSubject = new RealSubject();
		Subject cacheProxy = new CacheProxy(realSubject);

		ProxyPatternClient client = new ProxyPatternClient(cacheProxy);
		client.execute();
		client.execute();
		client.execute();
	}
}
