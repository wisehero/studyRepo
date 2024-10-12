package wisehero.springadvanced.decorator;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import wisehero.springadvanced.decorator.code.Component;
import wisehero.springadvanced.decorator.code.DecoratorPatternClient;
import wisehero.springadvanced.decorator.code.MessageDecorator;
import wisehero.springadvanced.decorator.code.RealComponent;
import wisehero.springadvanced.decorator.code.TimeDecorator;

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

	@Test
	void decorator2() {
		Component realComponent = new RealComponent();
		Component messageDecorator = new MessageDecorator(realComponent);
		Component timeDecorator = new TimeDecorator(messageDecorator);
		DecoratorPatternClient client = new DecoratorPatternClient(timeDecorator);

		client.execute();
	}
}
