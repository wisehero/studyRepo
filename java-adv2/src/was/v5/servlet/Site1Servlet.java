package was.v5.servlet;

import java.io.IOException;

import was.httpserver.HttpRequest;
import was.httpserver.HttpResponse;
import was.httpserver.HttpServlet;

public class Site1Servlet implements HttpServlet {
	@Override
	public void service(HttpRequest request, HttpResponse response) throws IOException {
		response.writeBody("<h1>site1</h1>");
	}
}
