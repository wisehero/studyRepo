package stream.basic;

import java.util.List;
import java.util.stream.Stream;

public class DuplicateExecutionMain {

	public static void main(String[] args) {
		// 스트림 중복 실행 확인
		Stream<Integer> stream = Stream.of(1, 2, 3);
		stream.forEach(System.out::println);

		// 스트림은 한 번만 사용 가능
		// stream.forEach(System.out::println);


		// 스트림을 복수개로 생성해서 사용
		List<Integer> list = List.of(1, 2, 3);
		Stream.of(list).forEach(System.out::println);
		Stream.of(list).forEach(System.out::println);
	}
}
