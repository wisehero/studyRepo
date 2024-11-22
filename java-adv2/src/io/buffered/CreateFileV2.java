package io.buffered;

import static io.buffered.BufferedConst.*;

import java.io.FileOutputStream;
import java.io.IOException;

public class CreateFileV2 {

	public static void main(String[] args) throws IOException {
		FileOutputStream fos = new FileOutputStream((FILE_NAME));
		long startTime = System.currentTimeMillis();

		byte[] buffer = new byte[BUFFER_SIZE];
		int bufferIndex = 0;

		for (int i = 0; i < FILE_SIZE; i++) {
			buffer[bufferIndex++] = 1;

			// 버퍼가 가득 차면 파일에 기록하고 버퍼를 비운다.
			if (bufferIndex == BUFFER_SIZE) {
				fos.write(buffer);
				bufferIndex = 0;
			}
		}

		if (bufferIndex > 0) {
			fos.write(buffer, 0, bufferIndex);
		}

		fos.close();

		long endTime = System.currentTimeMillis();
		System.out.println("File created: " + FILE_NAME);
		System.out.println("File size: " + FILE_SIZE / 1024 / 1024 + "MB");
		System.out.println("Time taken : " + (endTime - startTime) + "ms");
	}
}
