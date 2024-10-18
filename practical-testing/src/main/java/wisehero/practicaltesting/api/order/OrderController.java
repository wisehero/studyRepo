package wisehero.practicaltesting.api.order;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import wisehero.practicaltesting.api.order.request.OrderCreateRequest;
import wisehero.practicaltesting.api.order.response.OrderResponse;
import wisehero.practicaltesting.application.order.OrderService;

@RestController
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@PostMapping("/api/v1/orders/new")
	public OrderResponse createOrder(@RequestBody OrderCreateRequest request) {
		LocalDateTime registeredDateTime = LocalDateTime.now();
		return orderService.createOrder(request, registeredDateTime);
	}
}
