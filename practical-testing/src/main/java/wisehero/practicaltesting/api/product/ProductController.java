package wisehero.practicaltesting.api.product;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import wisehero.practicaltesting.api.product.response.ProductResponse;
import wisehero.practicaltesting.application.product.ProductService;

@RestController
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@GetMapping("/api/v1/products/selling")
	public List<ProductResponse> getSellingProducts() {
		return productService.getSellingProducts();
	}
}
