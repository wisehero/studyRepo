package wisehero.springadvanced.trace.strategy;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import wisehero.springadvanced.trace.strategy.code.strategy.ContextV2;
import wisehero.springadvanced.trace.strategy.code.strategy.Strategy;
import wisehero.springadvanced.trace.strategy.code.strategy.StrategyLogic1;
import wisehero.springadvanced.trace.strategy.code.strategy.StrategyLogic2;

@Slf4j
public class ContextV2Test {

	@Test
	void strategyV1() {
		ContextV2 context = new ContextV2();
		context.execute(new StrategyLogic1());
		context.execute(new StrategyLogic2());
	}

	@Test
	void strategyV2() {
		ContextV2 context = new ContextV2();
		context.execute(new Strategy() {
			@Override
			public void call() {
				log.info("비즈니스 로직1 실행");
			}
		});
		context.execute(new Strategy() {
			@Override
			public void call() {
				log.info("비즈니스 로직2 실행");
			}
		});
	}

	@Test
	void strategyV3() {
		ContextV2 context = new ContextV2();
		context.execute(() -> log.info("비즈니스 로직1 실행"));
		context.execute(() -> log.info("비즈니스 로직2 실행"));
	}
}
