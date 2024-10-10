package wisehero.springadvanced.trace.strategy.code.strategy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StrategyLogic2 implements Strategy {

	@Override
	public void call() {
		log.info("전략2의 로직");
	}

}
