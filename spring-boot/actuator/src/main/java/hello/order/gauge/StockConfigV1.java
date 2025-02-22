package hello.order.gauge;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hello.order.OrderService;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Configuration
public class StockConfigV1 {

	@Bean
	public MyStockMetric myStockMetric(OrderService orderService, MeterRegistry meterRegistry) {
		return new MyStockMetric(orderService, meterRegistry);
	}

	@Slf4j
	static class MyStockMetric {
		private OrderService orderService;
		private MeterRegistry meterRegistry;

		public MyStockMetric(OrderService orderService, MeterRegistry meterRegistry) {
			this.orderService = orderService;
			this.meterRegistry = meterRegistry;
		}

		@PostConstruct
		public void init() {
			Gauge.builder("my.stock", orderService, serivce -> {
				log.info("stock gauge call");
				return orderService.getStock().get();
			}).register(meterRegistry);
		}
	}
}
