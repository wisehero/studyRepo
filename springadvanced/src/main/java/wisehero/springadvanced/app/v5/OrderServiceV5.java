package wisehero.springadvanced.app.v5;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import wisehero.springadvanced.trace.callback.TraceTemplate;
import wisehero.springadvanced.trace.logtrace.LogTrace;
import wisehero.springadvanced.trace.template.AbstractTemplate;

@Service
public class OrderServiceV5 {

	private final OrderRepositoryV5 orderRepository;
	private final TraceTemplate template;

	public OrderServiceV5(OrderRepositoryV5 orderRepository, LogTrace trace) {
		this.orderRepository = orderRepository;
		this.template = new TraceTemplate(trace);
	}

	public void orderItem(String itemId) {
		template.execute("OrderService.orderItem()", () -> {
			orderRepository.save(itemId);
			return null;
		});
	}
}
