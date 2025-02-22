package hello.order.v1;

import java.util.concurrent.atomic.AtomicInteger;

import hello.order.OrderService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderServiceV1 implements OrderService {

	private final MeterRegistry registry;
	private final AtomicInteger stock = new AtomicInteger(100);

	public OrderServiceV1(MeterRegistry registry) {
		this.registry = registry;
	}

	@Override
	public void order() {
		log.info("주문");
		stock.decrementAndGet();

		Counter.builder("my.order")
			.tag("class", this.getClass().getName())
			.tag("method", "order")
			.description("주문 발생 건")
			.register(registry).increment();
	}

	@Override
	public void cancel() {
		log.info("취소");
		stock.incrementAndGet();

		Counter.builder("my.order")
			.tag("class", this.getClass().getName())
			.tag("method", "cancel")
			.description("주문 취소 건")
			.register(registry).increment();
	}

	@Override
	public AtomicInteger getStock() {
		return stock;
	}
}
