package wisehero.practicaltesting.api.product.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import wisehero.practicaltesting.application.product.request.ProductCreateServiceRequest;
import wisehero.practicaltesting.domain.product.ProductSellingStatus;
import wisehero.practicaltesting.domain.product.ProductType;

@Builder
public record ProductCreateRequest(
	@NotNull(message = "상품 타입은 필수입니다.")
	ProductType type,
	@NotNull(message = "상품 판매상태는 필수입니다.")
	ProductSellingStatus sellingStatus,
	@NotBlank(message = "상품 이름은 필수입니다.")
	String name,
	@Positive(message = "상품 가격은 양수여야 합니다.")
	int price) {

	public ProductCreateServiceRequest toCreateServiceRequest() {
		return ProductCreateServiceRequest.builder()
			.type(type)
			.sellingStatus(sellingStatus)
			.name(name)
			.price(price)
			.build();
	}
}
