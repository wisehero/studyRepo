package wisehero.springadvanced.trace.template;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import wisehero.springadvanced.trace.template.code.AbstractTemplate;
import wisehero.springadvanced.trace.template.code.SubClassLogic1;
import wisehero.springadvanced.trace.template.code.SubClassLogic2;

@Slf4j
public class TemplateMethodTest {

	@Test
	void templateMethodV0() {
		logic1();
		logic2();
	}

	private void logic1() {
		long startTime = System.currentTimeMillis();

		log.info("비즈니스 로직1 실행");

		long endTime = System.currentTimeMillis();
		long resultTime = endTime - startTime;
		log.info("resultTime={}", resultTime);
	}

	private void logic2() {
		long startTime = System.currentTimeMillis();

		log.info("비즈니스 로직1 실행");

		long endTime = System.currentTimeMillis();
		long resultTime = endTime - startTime;
		log.info("resultTime={}", resultTime);
	}

	@Test
	void templateMethodV1() {
		AbstractTemplate template1 = new SubClassLogic1();
		template1.execute();

		AbstractTemplate template2 = new SubClassLogic2();
		template2.execute();
	}
}
