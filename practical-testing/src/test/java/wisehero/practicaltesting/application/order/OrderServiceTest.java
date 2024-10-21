package wisehero.practicaltesting.application.order;

import static org.assertj.core.api.Assertions.*;
import static wisehero.practicaltesting.domain.product.ProductType.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import wisehero.practicaltesting.api.order.request.OrderCreateRequest;
import wisehero.practicaltesting.api.order.response.OrderResponse;
import wisehero.practicaltesting.domain.order.OrderRepository;
import wisehero.practicaltesting.domain.orderproduct.OrderProductRepository;
import wisehero.practicaltesting.domain.product.Product;
import wisehero.practicaltesting.domain.product.ProductRepository;
import wisehero.practicaltesting.domain.product.ProductSellingStatus;
import wisehero.practicaltesting.domain.product.ProductType;
import wisehero.practicaltesting.domain.stock.Stock;
import wisehero.practicaltesting.domain.stock.StockRepository;

@ActiveProfiles("test")
@SpringBootTest
class OrderServiceTest {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderProductRepository orderProductRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private StockRepository stockRepository;

	@AfterEach
	void tearDown() {
		orderProductRepository.deleteAllInBatch();
		productRepository.deleteAllInBatch();
		orderRepository.deleteAllInBatch();
		stockRepository.deleteAllInBatch();
	}

	@Test
	@DisplayName("주문번호 리스트 받아 주문 생성한다.")
	void createOrder() {
		// given
		LocalDateTime registeredDateTime = LocalDateTime.now();

		Product product1 = createProduct(HANDMADE, "001", 1000);
		Product product2 = createProduct(HANDMADE, "002", 3000);
		Product product3 = createProduct(HANDMADE, "003", 5000);
		productRepository.saveAll(List.of(product1, product2, product3));

		OrderCreateRequest request = OrderCreateRequest.builder()
			.productsNumbers(List.of("001", "002"))
			.build();

		// when
		OrderResponse orderResponse = orderService.createOrder(request, registeredDateTime);

		// then
		assertThat(orderResponse.id()).isNotNull();

		assertThat(orderResponse)
			.extracting("registeredDateTime", "totalPrice") // OrderResponse의 registeredDateTime, totalPrice를 추출
			// registeredDateTime는 given의 LocalDateTime.now()와 같아야 하고 totalPrice는 4000이어야 한다.
			.contains(registeredDateTime, 4000);

		assertThat(orderResponse.products())
			.extracting("productNumber", "price")
			.containsExactlyInAnyOrder(
				tuple("001", 1000),
				tuple("002", 3000));

	}

	@Test
	@DisplayName("중복되는 상품번호 리스트로 주문을 생성할 수 있다.")
	void createOrderWithDuplicatedProductNumber() {
		// given
		LocalDateTime registeredDateTime = LocalDateTime.now();

		Product product1 = createProduct(HANDMADE, "001", 1000);
		Product product2 = createProduct(HANDMADE, "002", 3000);
		Product product3 = createProduct(HANDMADE, "003", 5000);
		productRepository.saveAll(List.of(product1, product2, product3));

		OrderCreateRequest request = OrderCreateRequest.builder()
			.productsNumbers(List.of("001", "001"))
			.build();

		// when
		OrderResponse orderResponse = orderService.createOrder(request, registeredDateTime);

		// then
		assertThat(orderResponse.id()).isNotNull();

		assertThat(orderResponse)
			.extracting("registeredDateTime", "totalPrice") // OrderResponse의 registeredDateTime, totalPrice를 추출
			// registeredDateTime는 given의 LocalDateTime.now()와 같아야 하고 totalPrice는 4000이어야 한다.
			.contains(registeredDateTime, 2000);

		assertThat(orderResponse.products())
			.extracting("productNumber", "price")
			.containsExactlyInAnyOrder(
				tuple("001", 1000),
				tuple("001", 1000));

	}

	@Test
	@DisplayName("재고와 관련된 상품이 포함되어 있는 주문번호 리스트를 받아 주문을 생성한다.")
	void createOrderWithStock() {
		// given
		LocalDateTime registeredDateTime = LocalDateTime.now();

		Product product1 = createProduct(BOTTLE, "001", 1000);
		Product product2 = createProduct(BAKERY, "002", 3000);
		Product product3 = createProduct(HANDMADE, "003", 5000);
		productRepository.saveAll(List.of(product1, product2, product3));

		Stock stock1 = Stock.create("001", 2);
		Stock stock2 = Stock.create("002", 2);
		stockRepository.saveAll(List.of(stock1, stock2));

		OrderCreateRequest request = OrderCreateRequest.builder()
			.productsNumbers(List.of("001", "001", "002", "003"))
			.build();

		// when
		OrderResponse orderResponse = orderService.createOrder(request, registeredDateTime);

		// then
		assertThat(orderResponse.id()).isNotNull();
		assertThat(orderResponse)
			.extracting("registeredDateTime", "totalPrice")
			.contains(registeredDateTime, 10000);
		assertThat(orderResponse.products()).hasSize(4)
			.extracting("productNumber", "price")
			.containsExactlyInAnyOrder(
				tuple("001", 1000),
				tuple("001", 1000),
				tuple("002", 3000),
				tuple("003", 5000));
	}

	@Test
	@DisplayName("재고가 부족한 상품으로 주문을 생성하려는 경우 예외가 발생한다.")
	void createOrderWithNoStock() {
		// given
		LocalDateTime registeredDateTime = LocalDateTime.now();

		Product product1 = createProduct(BOTTLE, "001", 1000);
		Product product2 = createProduct(BAKERY, "002", 3000);
		Product product3 = createProduct(HANDMADE, "003", 5000);
		productRepository.saveAll(List.of(product1, product2, product3));

		Stock stock1 = Stock.create("001", 2);
		Stock stock2 = Stock.create("002", 2);
		stock1.decreaseQuantity(1); // todo
		stockRepository.saveAll(List.of(stock1, stock2));

		OrderCreateRequest request = OrderCreateRequest.builder()
			.productsNumbers(List.of("001", "001", "002", "003"))
			.build();

		// when then
		assertThatThrownBy(() -> orderService.createOrder(request, registeredDateTime))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("재고가 부족합니다.");
	}

	private Product createProduct(ProductType type, String productNumber, int price) {
		return Product.builder()
			.type(type)
			.productNumber(productNumber)
			.price(price)
			.sellingStatus(ProductSellingStatus.SELLING)
			.name("메뉴 이름")
			.build();
	}
}