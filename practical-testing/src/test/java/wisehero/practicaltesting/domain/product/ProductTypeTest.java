package wisehero.practicaltesting.domain.product;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTypeTest {

	@Test
	@DisplayName("상품 타입이 재고를 차감해야하는 타입인지 확인한다.")
	void test1() {
		// given
		ProductType givenType = ProductType.HANDMADE;

		// when
		boolean result = ProductType.containsStockType(givenType);

		// then
		assertThat(result).isFalse();
	}

	@Test
	@DisplayName("상품 타입이 재고 관련 타입인지를 확인한다.")
	void test2() {
		// given
		ProductType givenType = ProductType.BAKERY;

		// when
		boolean result = ProductType.containsStockType(givenType);

		// then
		assertThat(result).isTrue();
	}
}