package lambda.lambda5;

import java.util.List;

import lambda.lambda5.filter.IntegerFilter;

public class FilterMainV4 {
	public static void main(String[] args) {
		List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

		// 짝수만 거르는 필터
		List<Integer> evenNumbers = IntegerFilter.filter(numbers, number -> number % 2 == 0);
		System.out.println("evenNumbers = " + evenNumbers);

		// 홀수만 거르는 필터
		List<Integer> oddNumbers = IntegerFilter.filter(numbers, number -> number % 2 == 1);
		System.out.println("oddNumbers = " + oddNumbers);
	}
}
