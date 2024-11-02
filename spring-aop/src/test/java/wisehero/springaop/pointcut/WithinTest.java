package wisehero.springaop.pointcut;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import wisehero.springaop.member.MemberServiceImpl;

public class WithinTest {

	AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
	Method helloMethod;

	@BeforeEach
	public void init() throws NoSuchMethodException {
		helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
	}

	@Test
	@DisplayName("wisehero.springaop.member.MemberServiceImpl 안의 모든 메서드에 적용")
	void withinExact() {
		pointcut.setExpression("within(wisehero.springaop.member.MemberServiceImpl)");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	@DisplayName("wisehero.springaop.member 하위 패키지에서 Service를 포함한 클래스에 적용")
	void withinStar() {
		pointcut.setExpression("within(wisehero.springaop.member.*Service*)");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	@DisplayName("wisehero.springaop 하위의 모든 클래스에 적용")
	void withinSubPackage() {
		pointcut.setExpression("within(wisehero.springaop..*)");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}
}
