package wisehero.springaop.proxyvs;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

import lombok.extern.slf4j.Slf4j;
import wisehero.springaop.member.MemberService;
import wisehero.springaop.member.MemberServiceImpl;

@Slf4j
public class ProxyCastingTest {

	@Test
	void jdkProxy() {
		MemberServiceImpl target = new MemberServiceImpl();
		ProxyFactory proxyFactory = new ProxyFactory(target);
		proxyFactory.setProxyTargetClass(false); // 동적 프록시

		// 프록시를 인터페이스로 캐스팅
		MemberService memberServiceProxy = (MemberService)proxyFactory.getProxy();

		log.info("proxy class={}", memberServiceProxy.getClass());

		// JDK 동적 프록시를 구현 클래스로 캐스팅 시도 실패, ClassCastException 예외 발생
		assertThrows(ClassCastException.class, () -> {
			MemberServiceImpl memberServiceImpl = (MemberServiceImpl)memberServiceProxy;
		});
	}
}
