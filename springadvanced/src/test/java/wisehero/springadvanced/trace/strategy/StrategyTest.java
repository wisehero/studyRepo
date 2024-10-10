package wisehero.springadvanced.trace.strategy;

import javax.naming.Context;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import wisehero.springadvanced.trace.strategy.code.strategy.ContextV1;
import wisehero.springadvanced.trace.strategy.code.strategy.Strategy;
import wisehero.springadvanced.trace.strategy.code.strategy.StrategyLogic1;
import wisehero.springadvanced.trace.strategy.code.strategy.StrategyLogic2;

@Slf4j
public class StrategyTest {

	@Test
	void strategyV1() {
		Strategy strategyLogic1 = new StrategyLogic1();
		ContextV1 context1 = new ContextV1(strategyLogic1);
		context1.execute();

		Strategy strategyLogic2 = new StrategyLogic2();
		ContextV1 context2 = new ContextV1(strategyLogic2);
		context2.execute();
	}

	@Test
	void strategyV2() {
		Strategy strategyLogic1 = new StrategyLogic1() {
			@Override
			public void call() {
				log.info("전략1의 로직");
			}
		};
		log.info("strategyLogic1={}", strategyLogic1.getClass());
		ContextV1 context1 = new ContextV1(strategyLogic1);

		Strategy strategyLogic2 = new StrategyLogic2() {
			@Override
			public void call() {
				log.info("전략2의 로직");
			}
		};
		log.info("strategyLogic2={}", strategyLogic2.getClass());
		ContextV1 context2 = new ContextV1(strategyLogic2);
		context2.execute();
	}

	@Test
	void strategyV3() {
		ContextV1 context1 = new ContextV1(new Strategy() {
			@Override
			public void call() {
				log.info("전략1의 로직");
			}
		});
		context1.execute();

		ContextV1 context2 = new ContextV1(new Strategy() {
			@Override
			public void call() {
				log.info("전략2의 로직");
			}
		});
		context2.execute();
	}

	@Test
	void strategyV4() {
		ContextV1 context1 = new ContextV1(() -> log.info("전략1의 로직"));
		context1.execute();

		ContextV1 context2 = new ContextV1(() -> log.info("전략2의 로직"));
		context2.execute();
	}
}
