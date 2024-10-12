package wisehero.springadvanced.templatemethodstrategy.app.v4;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import wisehero.springadvanced.templatemethodstrategy.trace.logtrace.LogTrace;
import wisehero.springadvanced.templatemethodstrategy.trace.template.AbstractTemplate;

@Service
@RequiredArgsConstructor
public class OrderServiceV4 {

	private final OrderRepositoryV4 orderRepository;
	private final LogTrace trace;

	public void orderItem(String itemId) {
		AbstractTemplate<Void> template = new AbstractTemplate<Void>(trace) {
			@Override
			protected Void call() {
				orderRepository.save(itemId);
				return null;
			}
		};
		template.execute("OrderService.orderItem()");
	}
}
