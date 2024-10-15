package wisehero.practicaltesting.application.product;

import static java.util.stream.Collectors.*;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import wisehero.practicaltesting.api.product.response.ProductResponse;
import wisehero.practicaltesting.domain.product.Product;
import wisehero.practicaltesting.domain.product.ProductRepository;
import wisehero.practicaltesting.domain.product.ProductSellingStatus;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;

	public List<ProductResponse> getSellingProducts() {
		List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());

		return products.stream()
			.map(product -> ProductResponse.of(product))
			.collect(toList());
	}
}
