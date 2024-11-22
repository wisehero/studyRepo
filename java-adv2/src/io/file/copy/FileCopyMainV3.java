package io.file.copy;

import static java.nio.file.StandardCopyOption.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileCopyMainV3 {
	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();

		Path source = Path.of("temp/copy.dat");
		Path target = Path.of("temp/copy_new.dat");
		Files.copy(source, target, REPLACE_EXISTING); // 운영체제의 파일 복사 기능을 사용

		long endTime = System.currentTimeMillis();
		System.out.println("Time taken: " + (endTime - startTime) + "ms");
	}
}
