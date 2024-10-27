package wisehero.practicaltesting.api.order.request;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import wisehero.practicaltesting.application.order.request.OrderCreateServiceRequest;

@Builder
public record OrderCreateRequest(
	@NotEmpty(message = "상품 번호 리스트는 필수입니다.")
	List<String> productNumbers) {
	// 레코드는 기본 생성자를 가지고 있다.

	public OrderCreateServiceRequest toServiceRequest() {
		return OrderCreateServiceRequest.builder()
			.productNumbers(productNumbers)
			.build();
	}
}
