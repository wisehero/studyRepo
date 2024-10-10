package wisehero.springadvanced.templatemethodstrategy.app.v2;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import wisehero.springadvanced.templatemethodstrategy.trace.TraceId;
import wisehero.springadvanced.templatemethodstrategy.trace.TraceStatus;
import wisehero.springadvanced.templatemethodstrategy.trace.hellotrace.HelloTraceV2;

@Service
@RequiredArgsConstructor
public class OrderServiceV2 {

	private final OrderRepositoryV2 orderRepository;
	private final HelloTraceV2 trace;

	public void orderItem(TraceId traceId, String itemId) {
		TraceStatus status = null;
		try {
			status = trace.beginSync(traceId, "OrderService.orderItem()");

			orderRepository.save(status.getTraceId(), itemId);

			trace.end(status);
		} catch (Exception e) {
			trace.exception(status, e);
			throw e;
		}
	}
}
