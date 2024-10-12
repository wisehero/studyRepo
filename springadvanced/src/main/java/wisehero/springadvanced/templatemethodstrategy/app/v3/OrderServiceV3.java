package wisehero.springadvanced.templatemethodstrategy.app.v3;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import wisehero.springadvanced.templatemethodstrategy.trace.TraceStatus;
import wisehero.springadvanced.templatemethodstrategy.trace.logtrace.LogTrace;

@Service
@RequiredArgsConstructor
public class OrderServiceV3 {

	private final OrderRepositoryV3 orderRepository;
	private final LogTrace trace;

	public void orderItem(String itemId) {
		TraceStatus status = null;
		try {
			status = trace.begin("OrderService.orderItem()");

			orderRepository.save(itemId);

			trace.end(status);
		} catch (Exception e) {
			trace.exception(status, e);
			throw e;
		}
	}
}
