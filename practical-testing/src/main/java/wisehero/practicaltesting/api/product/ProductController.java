package wisehero.practicaltesting.api.product;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import wisehero.practicaltesting.api.ApiResponse;
import wisehero.practicaltesting.api.product.request.ProductCreateRequest;
import wisehero.practicaltesting.api.product.response.ProductResponse;
import wisehero.practicaltesting.application.product.ProductService;

@RestController
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@PostMapping("/api/v1/products/new")
	public ApiResponse<ProductResponse> createProduct(@Valid @RequestBody ProductCreateRequest request) {
		return ApiResponse.ok(productService.createProduct(request.toCreateServiceRequest()));
	}

	@GetMapping("/api/v1/products/selling")
	public ApiResponse<List<ProductResponse>> getSellingProducts() {
		return ApiResponse.ok(productService.getSellingProducts());
	}
}
