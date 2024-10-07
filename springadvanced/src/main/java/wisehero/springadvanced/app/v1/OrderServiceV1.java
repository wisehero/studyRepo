package wisehero.springadvanced.app.v1;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import wisehero.springadvanced.trace.TraceStatus;
import wisehero.springadvanced.trace.hellotrace.HelloTraceV1;

@Service
@RequiredArgsConstructor
public class OrderServiceV1 {

	private final OrderRepositoryV1 orderRepository;
	private final HelloTraceV1 trace;

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
