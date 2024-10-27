package wisehero.practicaltesting.api.order.response;

import static java.util.stream.Collectors.*;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import wisehero.practicaltesting.api.product.response.ProductResponse;
import wisehero.practicaltesting.domain.order.Order;

@Builder
public record OrderResponse(
	Long id,
	int totalPrice,
	LocalDateTime registeredDateTime,
	List<ProductResponse> products
) {

	public static OrderResponse of(Order order) {
		return OrderResponse.builder()
			.id(order.getId())
			.totalPrice(order.getTotalPrice())
			.registeredDateTime(order.getRegisteredDateTime())
			.products(order.getOrderProducts().stream()
				.map(orderProduct -> ProductResponse.of(orderProduct.getProduct()))
				.collect(toList()))
			.build();
	}
}
