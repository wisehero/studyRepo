package wisehero.practicaltesting.domain.product;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ProductType {
	// HANDMADE는 재고를 세지 않는 상품이다.
	HANDMADE("제조 음료"),
	BOTTLE("병 음료"),
	BAKERY("베이커리");

	private final String text;

	public static boolean containsStockType(ProductType type) {
		return List.of(BOTTLE, BAKERY).contains(type);
	}
}
