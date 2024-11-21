package was.v8;

import java.io.IOException;
import java.util.List;

import was.httpserver.HttpServer;
import was.httpserver.HttpServlet;
import was.httpserver.ServletManager;
import was.httpserver.annotation.AnnotationServletV1;
import was.httpserver.annotation.AnnotationServletV2;
import was.httpserver.annotation.AnnotationServletV3;
import was.v5.servlet.DiscardServlet;
import was.v7.SearchControllerV7;
import was.v7.SiteControllerV7;

public class ServerMainV8 {

	private static final int PORT = 12345;

	public static void main(String[] args) throws IOException {
		List<Object> controllers = List.of(new SiteControllerV8(), new SearchControllerV8());
		// HttpServlet servlet = new AnnotationServletV2(controllers);
		HttpServlet servlet = new AnnotationServletV3(controllers);

		ServletManager servletManager = new ServletManager();
		servletManager.setDefaultServlet(servlet);
		servletManager.add("/favicon.ico", new DiscardServlet());
		HttpServer server = new HttpServer(PORT, servletManager);
		server.start();
	}
}
