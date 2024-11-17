package was.httpserver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import was.v5.servlet.NotFoundServlet;

public class ServletManager {
	private final Map<String, HttpServlet> servletMap = new HashMap<>();
	private HttpServlet defaultServlet;
	private HttpServlet notFoundErrorServlet = new NotFoundServlet();
	private HttpServlet internalServerErrorServlet = new NotFoundServlet();

	public ServletManager() {
	}

	public void add(String path, HttpServlet servlet) {
		servletMap.put(path, servlet);
	}

	public void setDefaultServlet(HttpServlet defaultServlet) {
		this.defaultServlet = defaultServlet;
	}

	public void setNotFoundErrorServlet(HttpServlet notFoundErrorServlet) {
		this.notFoundErrorServlet = notFoundErrorServlet;
	}

	public void setInternalErrorServlet(HttpServlet internalServerErrorServlet) {
		this.internalServerErrorServlet = internalServerErrorServlet;
	}

	public void execute(HttpRequest request, HttpResponse response) throws IOException {
		try {
			HttpServlet servlet = servletMap.getOrDefault(request.getPath(), defaultServlet);
			if (servlet == null) {
				throw new PageNotFoundException("request url= " + request.getPath());
			}
			servlet.service(request, response);
		} catch (PageNotFoundException e) {
			e.printStackTrace();
			notFoundErrorServlet.service(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			internalServerErrorServlet.service(request, response);
		}
	}
}
