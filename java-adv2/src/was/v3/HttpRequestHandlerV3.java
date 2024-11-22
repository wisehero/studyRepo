package was.v3;

import static java.nio.charset.StandardCharsets.*;
import static util.MyLogger.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URLDecoder;

public class HttpRequestHandlerV3 implements Runnable {

	private final Socket socket;

	public HttpRequestHandlerV3(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			process(socket);
		} catch (Exception e) {
			log(e);
		}
	}

	private void process(Socket socket) throws IOException {

		try (socket;
			 BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), UTF_8));
			 PrintWriter writer = new PrintWriter(socket.getOutputStream(), false, UTF_8);) {

			String requestString = requestToString(reader);
			if (requestString.contains("/favicon.ico")) {
				log("favicon 요청");
				return;
			}

			log("HTTP 요청 처리중...");
			System.out.println(requestString);

			log("HTTP 응답 생성중...");
			if (requestString.contains("GET /site1")) {
				site1(writer);
			} else if (requestString.contains("GET /site2")) {
				site2(writer);
			} else if (requestString.contains("GET /site3")) {
				site3(writer);
			} else if (requestString.contains("GET /search")) {
				search(writer, requestString);
			} else if (requestString.contains("GET /")) {
				home(writer);
			} else {
				notFound(writer);
			}

			log("HTTP 응답 전달 완료");
		}

	}

	private String requestToString(BufferedReader reader) throws IOException {
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			if (line.isEmpty()) {
				break;
			}
			sb.append(line).append("\n");
		}
		return sb.toString();
	}

	private static void home(PrintWriter writer) {
		writer.println("HTTP/1.1 200 OK");
		writer.println("Content-Type: text/html; charset=UTF-8");
		writer.println();
		writer.println("<h1>home</h1>");
		writer.println("<ul>");
		writer.println("<li><a href='/site1'>site1</a></li>");
		writer.println("<li><a href='/site2'>site2</a></li>");
		writer.println("<li><a href='/search?q=hello'>검색</a></li>");
		writer.println("</ul>");
		writer.flush();
	}

	private static void site1(PrintWriter writer) {
		writer.println("HTTP/1.1 200 OK");
		writer.println("Content-Type: text/html; charset=UTF-8");
		writer.println();
		writer.println("<h1>site1</h1>");
		writer.flush();
	}

	private static void site2(PrintWriter writer) {
		writer.println("HTTP/1.1 200 OK");
		writer.println("Content-Type: text/html; charset=UTF-8");
		writer.println();
		writer.println("<h1>site1</h1>");
		writer.flush();
	}

	private static void site3(PrintWriter writer) {
		writer.println("HTTP/1.1 200 OK");
		writer.println("Content-Type: text/html; charset=UTF-8");
		writer.println();
		writer.println("<h1>site1</h1>");
		writer.flush();
	}

	private static void notFound(PrintWriter writer) {
		writer.println("HTTP/1.1 404 NotFound");
		writer.println("Content-Type: text/html; charset=UTF-8");
		writer.println();
		writer.println("<h1>404 페이지를 찾을 수 없습니다.</h1>");
		writer.flush();
	}

	private static void search(PrintWriter writer, String requestString) {
		int startIndex = requestString.indexOf("q=");
		int endIndex = requestString.indexOf(" ", startIndex + 2);
		String query = requestString.substring(startIndex + 2, endIndex);
		String decode = URLDecoder.decode(query, UTF_8);

		writer.println("HTTP/1.1 200 OK");
		writer.println("Content-Type: text/html; charset=UTF-8");
		writer.println();
		writer.println("<h1>Search</h1>");
		writer.println("<ul>");
		writer.println("<li>query: " + query + "</li>");
		writer.println("<li>decode: " + decode + "</li>");
		writer.println("</ul>");
		writer.flush();
	}
}
