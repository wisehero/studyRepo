package wisehero.practicaltesting.api.product.response;

import lombok.Builder;
import wisehero.practicaltesting.domain.product.Product;
import wisehero.practicaltesting.domain.product.ProductSellingStatus;
import wisehero.practicaltesting.domain.product.ProductType;

@Builder
public record ProductResponse(
	Long id,
	String productNumber,
	ProductType type,
	ProductSellingStatus sellingStatus,
	String name,
	int price
) {
	public static ProductResponse of(Product product) {
		return new ProductResponse(
			product.getId(),
			product.getProductNumber(),
			product.getType(),
			product.getSellingStatus(),
			product.getName(),
			product.getPrice());
	}
}
