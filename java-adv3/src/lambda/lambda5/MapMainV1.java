package lambda.lambda5;

import java.util.ArrayList;
import java.util.List;

public class MapMainV1 {

	public static void main(String[] args) {
		List<String> list = List.of("1", "12", "123", "1234");

		// 문자열의 길이를 구하기
		List<Integer> lengths = mapStringToLength(list);
		System.out.println("lengths = " + lengths);

		// 문자열을 숫자로 변환
		List<Integer> numbers = mapStringToInteger(list);
		System.out.println("numbers = " + numbers);
	}

	static List<Integer> mapStringToInteger(List<String> list) {
		List<Integer> numbers = new ArrayList<>();
		for (String s : list) {
			Integer value = Integer.valueOf(s);
			numbers.add(value);
		}
		return numbers;
	}

	static List<Integer> mapStringToLength(List<String> list) {
		List<Integer> numbers = new ArrayList<>();
		for (String s : list) {
			Integer value = s.length();
			numbers.add(value);
		}
		return numbers;
	}
}
