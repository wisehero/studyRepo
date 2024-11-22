package wisehero.practicaltesting.domain.stock;

import static org.assertj.core.api.Assertions.*;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

class StockTest {

	@Test
	@DisplayName("재고의 수량이 제공된 수량보다 작은지 확인한다.")
	void isQuantityLessThan() {
		// given
		int quantity = 2;
		Stock stock = Stock.create("001", 1);

		// when
		boolean result = stock.isQuantityLessThan(quantity);

		// then
		assertThat(result).isTrue();
	}

	@Test
	@DisplayName("재고를 주어진 개수만큼 차감할 수 있다.")
	void decreaseQuantity() {
		// given
		Stock stock = Stock.create("001", 1);
		int quantity = 1;

		// when
		stock.decreaseQuantity(quantity);

		// then
		assertThat(stock.getQuantity()).isZero();
	}

	@Test
	@DisplayName("재고보다 많은 수의 수량으로 차감 시도하는 경우 예외가 발생한다.")
	void deductQuantity2() {
		// given
		Stock stock = Stock.create("001", 1);
		int quantity = 2;

		// when then
		assertThatThrownBy(() -> stock.decreaseQuantity(quantity))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("차감할 수량이 없습니다.");
	}

	@DisplayName("재고 차감 시나리오")
	@TestFactory
	Collection<DynamicTest> stockDeductionDynamicTest() {
		// given
		Stock stock = Stock.create("001", 1);

		return List.of(
			DynamicTest.dynamicTest("재고를 주어진 개수만큼 차감할 수 있다.", () -> {
				// given
				int quantity = 1;

				// when
				stock.decreaseQuantity(quantity);

				// then
				assertThat(stock.getQuantity()).isZero();
			}),
			DynamicTest.dynamicTest("재고보다 많은 수의 수량으로 차감 시도하는 경우 예외가 발생한다.", () -> {
				// given
				int quantity = 1;

				// expected
				assertThatThrownBy(() -> stock.decreaseQuantity(quantity))
					.isInstanceOf(IllegalArgumentException.class)
					.hasMessage("차감할 수량이 없습니다.");
			})
		);
	}
}