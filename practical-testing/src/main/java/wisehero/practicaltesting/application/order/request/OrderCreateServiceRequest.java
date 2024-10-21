package wisehero.practicaltesting.application.order.request;

import java.util.List;

import lombok.Builder;

@Builder
public record OrderCreateServiceRequest(
	List<String> productNumbers
) {
}
