package wisehero.practicaltesting.domain.product;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

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

	@DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
	@CsvSource({"HANDMADE, false", "BOTTLE, true", "BAKERY, true"})
	@ParameterizedTest
	void test3(ProductType productType, boolean expected) {
		// when
		boolean result = ProductType.containsStockType(productType);

		// then
		assertThat(result).isEqualTo(expected);
	}

	private static Stream<Arguments> provideProductTypesForCheckingStockType() {
		return Stream.of(
			Arguments.of(ProductType.HANDMADE, false),
			Arguments.of(ProductType.BOTTLE, true),
			Arguments.of(ProductType.BAKERY, true)
		);
	}

	@DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
	@MethodSource("provideProductTypesForCheckingStockType")
	@ParameterizedTest
	void test4(ProductType productType, boolean expected) {
		// when
		boolean result = ProductType.containsStockType(productType);

		// then
		assertThat(result).isEqualTo(expected);
	}
}