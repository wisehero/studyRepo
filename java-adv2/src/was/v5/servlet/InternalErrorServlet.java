package was.v5.servlet;

import was.httpserver.HttpRequest;
import was.httpserver.HttpResponse;
import was.httpserver.HttpServlet;

public class InternalErrorServlet implements HttpServlet {

	@Override
	public void service(HttpRequest request, HttpResponse response) {
		response.setStatus(500);
		response.writeBody("<h1>500 Internal Server Error</h1>");
	}
}
