package wisehero.springadvanced.app.v3;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import wisehero.springadvanced.trace.TraceStatus;
import wisehero.springadvanced.trace.hellotrace.HelloTraceV2;
import wisehero.springadvanced.trace.logtrace.LogTrace;

@RestController
@RequiredArgsConstructor
public class OrderControllerV3 {

	private final OrderServiceV3 orderService;
	private final LogTrace logTrace;

	@GetMapping("/v3/request")
	public String request(String itemId) {
		TraceStatus status = null;

		try {
			status = logTrace.begin("OrderController.request()");

			orderService.orderItem(itemId);

			logTrace.end(status);
			return "ok";
		} catch (Exception e) {
			logTrace.exception(status, e);
			throw e;
		}
	}
}
