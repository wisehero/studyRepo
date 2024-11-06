package io.file.text;

import static java.nio.charset.StandardCharsets.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class ReadTextFileV2 {

	public static final String PATH = "temp/hello2.txt";

	public static void main(String[] args) throws IOException {
		String writeString = "abc\n가나다";
		System.out.println("== Write String ==");
		System.out.println(writeString);

		Path path = Path.of(PATH);

		// 파일에 쓰기
		Files.writeString(path, writeString, UTF_8);

		// 파일에서 읽기
		System.out.println("== Read String ==");
		List<String> lines = Files.readAllLines(path, UTF_8);
		for (int i = 0; i < lines.size(); i++) {
			System.out.println((i + 1) + ": " + lines.get(i));
		}

		// 한 줄씩 메모리 사용량을 줄이고 싶다면 아래의 기능을 사용.
		// 파일이 아주 크다면 한 줄씩 읽는게 좋은 선택이다.
		// 자바는 한 번에 1MB의 데이터만 메모리에 올려서 처리한다. 따라서 너무 크다면 아래처럼 처리한다.
		try (Stream<String> lineStream = Files.lines(path)) {
			lineStream.forEach(System.out::println);
		}
	}
}
