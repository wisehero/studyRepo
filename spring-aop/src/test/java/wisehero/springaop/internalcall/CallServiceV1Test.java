package wisehero.springaop.internalcall;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import wisehero.springaop.internalcall.aop.CallLogAspect;

@Import(CallLogAspect.class)
@SpringBootTest
public class CallServiceV1Test {

	@Autowired
	CallServiceV1 callServiceV1;

	@Test
	void external() {
		callServiceV1.external();
	}
}
