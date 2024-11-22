package was.v7;

import java.io.IOException;
import java.util.List;

import was.httpserver.HttpServer;
import was.httpserver.HttpServlet;
import was.httpserver.ServletManager;
import was.httpserver.annotation.AnnotationServletV1;
import was.v5.servlet.DiscardServlet;

public class ServerMainV7 {

	private static final int PORT = 12345;

	public static void main(String[] args) throws IOException {
		List<Object> controllers = List.of(new SiteControllerV7(), new SearchControllerV7());
		HttpServlet servlet = new AnnotationServletV1(controllers);

		ServletManager servletManager = new ServletManager();
		servletManager.setDefaultServlet(servlet);
		servletManager.add("/favicon.ico", new DiscardServlet());
		HttpServer server = new HttpServer(PORT, servletManager);
		server.start();
	}
}
