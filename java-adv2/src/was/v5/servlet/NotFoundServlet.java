package was.v5.servlet;

import was.httpserver.HttpRequest;
import was.httpserver.HttpResponse;
import was.httpserver.HttpServlet;

public class NotFoundServlet implements HttpServlet {

	@Override
	public void service(HttpRequest request, HttpResponse response) {
		response.setStatus(404);
		response.writeBody("<h1>404 Not Found</h1>");
	}
}
