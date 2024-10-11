package wisehero.springadvanced.decorator;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import wisehero.springadvanced.decorator.code.Component;
import wisehero.springadvanced.decorator.code.DecoratorPatternClient;
import wisehero.springadvanced.decorator.code.MessageDecorator;
import wisehero.springadvanced.decorator.code.RealComponent;

@Slf4j
public class DecoratorPatternTest {

	@Test
	void noDecorator() {
		Component realComponent = new RealComponent();
		DecoratorPatternClient client = new DecoratorPatternClient(realComponent);

		client.execute();
	}

	@Test
	void decorator1() {
		Component realComponent = new RealComponent();
		Component messageDecorator = new MessageDecorator(realComponent);
		DecoratorPatternClient client = new DecoratorPatternClient(messageDecorator);

		client.execute();
	}
}
