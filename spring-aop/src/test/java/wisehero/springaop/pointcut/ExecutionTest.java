package wisehero.springaop.pointcut;

import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.Method;

import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import lombok.extern.slf4j.Slf4j;
import wisehero.springaop.member.MemberServiceImpl;

@Slf4j
public class ExecutionTest {

	AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
	Method helloMethod;

	@BeforeEach
	public void init() throws NoSuchMethodException {
		helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
	}

	@Test
	void printMethod() {
		log.info("helloMethod={}", helloMethod);
	}

	@Test
	@DisplayName("정확하게 일치한 포인트컷")
	void exactMatch() {
		pointcut.setExpression(
			// 접근제어자? 반환타입 선언타입? 메서드이름 파라미터 예외? 순서다. 물음표는 생략 가능
			"execution(public String wisehero.springaop.member.MemberServiceImpl.hello(java.lang.String))");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	@DisplayName("가장 많이 생략한 포인트컷")
	void allMatch() {
		pointcut.setExpression("execution(* *(..))");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	@DisplayName("메서드이름 매칭 - hello 포함")
	void nameMatch() {
		pointcut.setExpression("execution(* hello(..))");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	@DisplayName("메서드 이름 매칭 - hel 포함")
	void nameMatch1() {
		pointcut.setExpression("execution(* hel*(..))");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	@DisplayName("메서드 이름 매칭 - el 포함")
	void nameMatch2() {
		pointcut.setExpression("execution(* *el*(..))");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	@DisplayName("메서드 이름 매칭 - nono 포함")
	void nameMatchFalse() {
		pointcut.setExpression("execution(* nono*(..))");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
	}

	@Test
	@DisplayName("패키지 이름 매칭")
	void packageExactMatch() {
		pointcut.setExpression("execution(* wisehero.springaop.member.MemberServiceImpl.hello(..))");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	@DisplayName("패키지 이름 매칭 - 패키지명만 일치")
	void packageExactMatch2() {
		pointcut.setExpression("execution(* wisehero.springaop.member.*.*(..))");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	@DisplayName("패키지 이름 매칭 - 불일치")
	void packageExactMatchFalse() {
		pointcut.setExpression("execution(* wisehero.springaop.*.*(..))"); // member가 빠짐
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
	}

	@Test
	@DisplayName("패키지 하위 패키지 매칭")
	void packageMatchSubPackage1() {
		pointcut.setExpression("execution(* wisehero.springaop..*.*(..))");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	@DisplayName("패키지 하위 패키지 매칭 2")
	void packageMatchSubPackage2() {
		pointcut.setExpression("execution(* wisehero.springaop..*(..))");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	@DisplayName("하나의 정확한 타입을 매치")
	void typeExactMatch() {
		pointcut.setExpression("execution(* wisehero.springaop.member.MemberServiceImpl.*(..))");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	@DisplayName("부모의 타입도 매치")
	void typeMatchSuperType() {
		pointcut.setExpression("execution(* wisehero.springaop.member.MemberService.*(..))");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	@DisplayName("MemberServiceImpl을 선언했기 때문에 내부의 internal도 매치")
	void typeMatchInternal() throws NoSuchMethodException {
		pointcut.setExpression("execution(* wisehero.springaop.member.MemberServiceImpl.*(..))");
		Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
		assertThat(pointcut.matches(internalMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	@DisplayName("MemberService 인터페이스에는 internal이 없으므로 매치하지 않음")
	void typeMatchNoSuperTypeMethodFalse() throws NoSuchMethodException {
		pointcut.setExpression("execution(* wisehero.springaop.member.MemberService.*(..))");
		Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
		assertThat(pointcut.matches(internalMethod, MemberServiceImpl.class)).isFalse();
	}

	@Test
	@DisplayName("String 파라미터 매칭")
	void argsMatch() {
		pointcut.setExpression("execution(* *(String))");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	@DisplayName("파라미터가 없는 것 매칭")
	void argsMatchNoArgs() {
		pointcut.setExpression("execution(* *())");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
	}

	@Test
	@DisplayName("정확히 하나의 파라미터 허용, 파라미터에 모든 타입 허용")
	void argsMatchOneParam() {
		pointcut.setExpression("execution(* *(*))");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	@DisplayName("파라미터 갯수 무관, 파라미터 타입 무관")
	void argsMatchAnyParam() {
		pointcut.setExpression("execution(* *(..))");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	@DisplayName("String 타입으로 시작하는 매개변수를 받고 이후에는 모든 타입의 파라미터를 숫자 제한없이 매치")
	void argsMatchComplex() {
		pointcut.setExpression("execution(* *(String, ..))");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

}
