package wisehero.springadvanced.app.v5;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import wisehero.springadvanced.trace.callback.TraceTemplate;
import wisehero.springadvanced.trace.logtrace.LogTrace;
import wisehero.springadvanced.trace.template.AbstractTemplate;

@RestController
public class OrderControllerV5 {

	private final OrderServiceV5 orderService;
	private final TraceTemplate template;

	public OrderControllerV5(OrderServiceV5 orderService, LogTrace trace) {
		this.orderService = orderService;
		this.template = new TraceTemplate(trace);
	}

	@GetMapping("/v5/request")
	public String request(String itemId) {
		return template.execute("OrderController.request()", () -> {
			orderService.orderItem(itemId);
			return "ok";
		});
	}
}
