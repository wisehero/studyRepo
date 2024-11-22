package chat.client;

import static util.MyLogger.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class WriteHandler implements Runnable {

	private static final String DELEMETER = "|";

	private final DataOutputStream output;
	private final Client client;

	private boolean closed = false;

	public WriteHandler(DataOutputStream output, Client client) {
		this.output = output;
		this.client = client;
	}

	@Override
	public void run() {
		Scanner scanner = new Scanner(System.in);
		try {
			String username = inputUsername(scanner);
			output.writeUTF("/join" + DELEMETER + username);

			while (true) {
				String toSend = scanner.nextLine();
				if (toSend.isEmpty()) {
					continue;
				}

				if (toSend.equals("/exit")) {
					output.writeUTF(toSend);
					break;
				}

				// "/"로 시작하면 명령어, 나머지는 일반 메시지
				if (toSend.startsWith("/")) {
					output.writeUTF(toSend);
				} else {
					output.writeUTF("/message" + DELEMETER + toSend);
				}
			}
		} catch (IOException | NoSuchElementException e) {
			log(e);
		} finally {
			client.close();
		}
	}

	private String inputUsername(Scanner scanner) {
		System.out.println("이름을 입력하세요.");
		String username;
		do {
			username = scanner.nextLine();
		} while (username.isEmpty());
		return username;
	}

	public synchronized void close() {
		if (closed) {
			return;
		}
		try {
			System.in.close();
		} catch (IOException e) {
			log(e);
		}
		closed = true;
		log("writeHandler 종료");
	}

}
