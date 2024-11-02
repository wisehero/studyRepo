package wisehero.springaop.proxyvs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import lombok.extern.slf4j.Slf4j;
import wisehero.springaop.member.MemberService;
import wisehero.springaop.member.MemberServiceImpl;
import wisehero.springaop.proxyvs.code.ProxyDIAspect;

@Slf4j
@SpringBootTest(properties = {"spring.aop.proxy-target-class=false"}) // 동적 프록시 활성
@Import(ProxyDIAspect.class)
public class ProxyDITest {

	@Autowired
	MemberService memberService;

	@Autowired
	MemberServiceImpl memberServiceImpl;

	@Test
	void go() {
		log.info("memberService class={}", memberService.getClass());
		log.info("memberServiceImpl class={}", memberServiceImpl.getClass());
		memberServiceImpl.hello("hello");
	}
}
