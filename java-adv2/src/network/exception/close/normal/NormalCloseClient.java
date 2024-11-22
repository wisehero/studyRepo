package network.exception.close.normal;

import static util.MyLogger.*;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class NormalCloseClient {

	public static void main(String[] args) throws IOException, InterruptedException {
		Socket socket = new Socket("localhost", 12345);
		log("소켓 연결: " + socket);
		InputStream input = socket.getInputStream();

		readByInputStream(input, socket);
		readByBufferedReader(input, socket);
		readByDataInputStream(input, socket);

		log("연결 종료: " + socket.isClosed());
	}

	private static void readByInputStream(InputStream input, Socket socket) throws IOException {
		int read = input.read();
		log("read = " + read);
		if (read == -1) {
			input.close();
			socket.close();
		}
	}

	private static void readByBufferedReader(InputStream input, Socket socket) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(input));
		String readString = br.readLine();
		log("readString = " + readString);
		if (readString == null) {
			br.close();
			socket.close();
		}
	}

	private static void readByDataInputStream(InputStream input, Socket socket) throws IOException {
		DataInputStream dis = new DataInputStream(input);
		try {
			dis.readUTF();
		} catch (EOFException e) {
			log(e);
		} finally {
			dis.close();
			socket.close();
		}
	}
}
