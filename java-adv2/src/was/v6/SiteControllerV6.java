package was.v6;

import was.httpserver.HttpRequest;
import was.httpserver.HttpResponse;

public class SiteControllerV6 {

	public void site1(HttpRequest request, HttpResponse response) {
		response.writeBody("<h1>Site1</h1>");
	}

	public void site2(HttpRequest request, HttpResponse response) {
		response.writeBody("<h1>Site2</h1>");
	}

	public void search(HttpRequest request, HttpResponse response) {
		String query = request.getParameter("q");
		response.writeBody("<h1>Search</h1>");
		response.writeBody("<ul>");
		response.writeBody("<li>query = " + query + "</li>");
		response.writeBody("</ul>");
	}
}
