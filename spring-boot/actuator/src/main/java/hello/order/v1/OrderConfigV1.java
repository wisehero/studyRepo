package hello.order.v1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hello.order.OrderService;
import hello.order.v0.OrderServiceV0;
import io.micrometer.core.instrument.MeterRegistry;

@Configuration
public class OrderConfigV1 {

	@Bean
	OrderService orderService(MeterRegistry meterRegistry) {
		return new OrderServiceV1(meterRegistry);
	}
}
