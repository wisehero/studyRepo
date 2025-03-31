package lambda.lambda5.ex;

import java.util.ArrayList;
import java.util.List;

import lambda.lambda5.filter.GenericFilter;
import lambda.lambda5.map.GenericMapper;

public class Ex1_Number {

	public static void main(String[] args) {
		// 짝수만 남기고, 남은 값의 2배를 반환
		List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
	}

	static List<Integer> direct(List<Integer> numbers) {
		List<Integer> result = new ArrayList<>();
		for (Integer number : numbers) {
			if (number % 2 == 0) {
				result.add(number * 2);
			}
		}
		return result;
	}

	static List<Integer> lambda(List<Integer> numbers) {
		List<Integer> evenNumbers = GenericFilter.filter(numbers, e -> e % 2 == 0);
		return GenericMapper.map(evenNumbers, e -> e * 2);
	}
}
