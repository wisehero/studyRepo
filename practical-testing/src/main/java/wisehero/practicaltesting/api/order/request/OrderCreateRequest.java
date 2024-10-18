package wisehero.practicaltesting.api.order.request;

import java.util.List;

import lombok.Builder;

@Builder
public record OrderCreateRequest(List<String> productsNumbers) {
	// 레코드는 기본 생성자를 가지고 있다.
}
