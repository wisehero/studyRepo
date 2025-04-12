package stream.basic;

import java.util.List;

public class LazyEvalMain3 {

	public static void main(String[] args) {
		List<Integer> data = List.of(1, 2, 3, 4, 5, 6);
		ex1(data);
		ex2(data);
	}

	private static void ex1(List<Integer> data) {
		System.out.println("== MyStreamV3 시작 ==");
		Integer result = MyStreamV3.of(data)
			.filter(i -> {
				boolean isEven = i % 2 == 0;
				System.out.println("filter() 실행 : " + i + "(" + isEven + ")");

				return isEven;
			})
			.map(i -> {
				int mapped = i * 10;
				System.out.println("map() 실행: " + i + " -> " + mapped);
				return mapped;
			})
			.getFirst();
		System.out.println("result = " + result);
		System.out.println("== MyStreamV3 종료 ==");
	}

	private static void ex2(List<Integer> data) {
		System.out.println("== Stream API 시작 ==");
		Integer first = data.stream()
			.filter(i -> {
				boolean isEven = i % 2 == 0;
				System.out.println("filter() 실행: " + i + "(" + isEven + ")");

				return isEven;
			})
			.map(i -> {
				int mapped = i * 10;
				System.out.println("map() 실행: " + i + " -> " + mapped);

				return mapped;
			})
			.findFirst().get();
		System.out.println("result = " + first);
		System.out.println("== Stream API 종료 ==");
	}
}
