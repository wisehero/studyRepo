package hello.order.v2;

import java.util.concurrent.atomic.AtomicInteger;

import hello.order.OrderService;
import io.micrometer.core.annotation.Counted;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderServiceV2 implements OrderService {

	private AtomicInteger stock = new AtomicInteger(100);

	@Counted(value = "my.order", description = "주문 발생 건")
	@Override
	public void order() {
		log.info("주문");
		stock.decrementAndGet();
	}

	@Counted(value = "my.order", description = "주문 취소 건")
	@Override
	public void cancel() {
		log.info("취소");
		stock.incrementAndGet();
	}

	@Override
	public AtomicInteger getStock() {
		return stock;
	}
}
