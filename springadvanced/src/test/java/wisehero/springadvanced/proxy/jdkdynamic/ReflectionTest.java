package wisehero.springadvanced.proxy.jdkdynamic;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReflectionTest {

	@Test
	void reflection0() {
		Hello target = new Hello();

		// 공통 로직1 시작
		log.info("start");
		String result1 = target.callA();
		log.info("result={}", result1);
		//공통 로직1 종료

		// 공통 로직2 시작
		log.info("start");
		String result2 = target.callB();
		log.info("result={}", result2);
		// 공통 로직2 종료

		// 위의 로직은 메서드 호출부만 제외한 나머지가 모두 중복이다.
	}

	@Test
	void reflection1() throws Exception {
		// 클래스 정보
		Class classHello = Class.forName("wisehero.springadvanced.proxy.jdkdynamic.ReflectionTest$Hello");

		Hello target = new Hello();
		// callA 메서드 정보
		Method methodCallA = classHello.getMethod("callA");
		// invoke는 획득한 메서드 메타정보로 실제 인스턴스의 메서드를 호출
		Object result1 = methodCallA.invoke(target);
		log.info("result1={}", result1);

		// callB 메서드 정보
		Method methodCallB = classHello.getMethod("callB");
		Object result2 = methodCallB.invoke(target);
		log.info("result2={}", result2);
	}

	@Test
	void reflection2() throws Exception {
		Class classHello = Class.forName("wisehero.springadvanced.proxy.jdkdynamic.ReflectionTest$Hello");

		Hello target = new Hello();
		Method methodCallA = classHello.getMethod("callA"); // 여기서 런타임 에러 발생 가능성 callA가 아니라 callZ라면?
		dynamicCall(methodCallA, target);

		Method methodCallB = classHello.getMethod("callB");
		dynamicCall(methodCallB, target);
	}

	private void dynamicCall(Method method, Object object) throws Exception {
		log.info("start");
		Object result = method.invoke(object);
		log.info("result={}", result);

	}

	@Slf4j
	static class Hello {
		public String callA() {
			log.info("callA");
			return "A";
		}

		public String callB() {
			log.info("callB");
			return "B";
		}
	}

}
