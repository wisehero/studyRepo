package wisehero.practicaltesting.api.product.request;

import lombok.Builder;
import wisehero.practicaltesting.domain.product.Product;
import wisehero.practicaltesting.domain.product.ProductSellingStatus;
import wisehero.practicaltesting.domain.product.ProductType;

@Builder
public record ProductCreateRequest(ProductType type,
								   ProductSellingStatus sellingStatus,
								   String name,
								   int price) {

	public Product toEntity(String nextProductNumber) {
		return Product.builder()
			.productNumber(nextProductNumber)
			.type(type)
			.sellingStatus(sellingStatus)
			.name(name)
			.price(price)
			.build();
	}
}
