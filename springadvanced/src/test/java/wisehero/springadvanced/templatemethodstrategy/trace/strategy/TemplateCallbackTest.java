package wisehero.springadvanced.templatemethodstrategy.trace.strategy;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import wisehero.springadvanced.templatemethodstrategy.trace.strategy.code.template.Callback;
import wisehero.springadvanced.templatemethodstrategy.trace.strategy.code.template.TimeLogTemplate;

@Slf4j
public class TemplateCallbackTest {

	@Test
	void callbackV1() {
		TimeLogTemplate template = new TimeLogTemplate();

		template.execute(new Callback() {
			@Override
			public void call() {
				log.info("비즈니스 로직1 실행");
			}
		});

		template.execute(new Callback() {
			@Override
			public void call() {
				log.info("비즈니스 로직2 실행");
			}
		});
	}

	@Test
	void callbackV2() {
		TimeLogTemplate template = new TimeLogTemplate();

		template.execute(() -> log.info("비즈니스 로직1 실행"));
		template.execute(() -> log.info("비즈니스 로직2 실행"));
	}
}
