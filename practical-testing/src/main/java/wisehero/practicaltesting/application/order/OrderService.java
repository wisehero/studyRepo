package wisehero.practicaltesting.application.order;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import wisehero.practicaltesting.api.order.request.OrderCreateRequest;
import wisehero.practicaltesting.api.order.response.OrderResponse;
import wisehero.practicaltesting.application.order.request.OrderCreateServiceRequest;
import wisehero.practicaltesting.domain.order.Order;
import wisehero.practicaltesting.domain.order.OrderRepository;
import wisehero.practicaltesting.domain.product.Product;
import wisehero.practicaltesting.domain.product.ProductRepository;
import wisehero.practicaltesting.domain.product.ProductType;
import wisehero.practicaltesting.domain.stock.Stock;
import wisehero.practicaltesting.domain.stock.StockRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

	private final ProductRepository productRepository;
	private final OrderRepository orderRepository;
	private final StockRepository stockRepository;

	public OrderResponse createOrder(OrderCreateServiceRequest request, LocalDateTime registeredDateTime) {
		List<String> productNumbers = request.productNumbers();
		List<Product> products = findProductsBy(productNumbers);

		decreaseStockQuantity(products);

		Order order = Order.create(products, registeredDateTime);
		Order savedOrder = orderRepository.save(order);
		return OrderResponse.of(savedOrder);
	}

	private void decreaseStockQuantity(List<Product> products) {
		// 재고 차감 체크가 필요한 상품들 filter
		List<String> stockProductNumbers = extractStockProductNumbers(products);

		// 재고 엔티티 조회
		Map<String, Stock> stockMap = createStockMapBy(stockProductNumbers);
		// 상품별 counting
		Map<String, Long> productCountingMap = createCountingMapBy(stockProductNumbers);

		for (String stockProductNumber : new HashSet<>(stockProductNumbers)) {
			Stock stock = stockMap.get(stockProductNumber);
			int quantity = productCountingMap.get(stockProductNumber).intValue();

			if (stock.isQuantityLessThan(quantity)) {
				throw new IllegalArgumentException("재고가 부족합니다.");
			}
			stock.decreaseQuantity(quantity);
		}
	}

	private List<Product> findProductsBy(List<String> productNumbers) {
		List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);
		Map<String, Product> productMap = products.stream()
			.collect(Collectors.toMap(Product::getProductNumber, p -> p));

		return productNumbers.stream()
			.map(productMap::get)
			.toList();
	}

	private static List<String> extractStockProductNumbers(List<Product> products) {
		return products.stream()
			.filter(product -> ProductType.containsStockType(product.getType()))
			.map(Product::getProductNumber)
			.toList();
	}

	private static Map<String, Long> createCountingMapBy(List<String> stockProductNumbers) {
		return stockProductNumbers.stream()
			.collect(Collectors.groupingBy(p -> p, Collectors.counting()));
	}

	private Map<String, Stock> createStockMapBy(List<String> stockProductNumbers) {
		List<Stock> stocks = stockRepository.findAllByProductNumberIn(stockProductNumbers);
		return stocks.stream().collect(Collectors.toMap(Stock::getProductNumber, s -> s));
	}
}
