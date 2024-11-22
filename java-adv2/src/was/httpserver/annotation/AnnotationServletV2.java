package was.httpserver.annotation;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import was.httpserver.HttpRequest;
import was.httpserver.HttpResponse;
import was.httpserver.HttpServlet;
import was.httpserver.PageNotFoundException;

public class AnnotationServletV2 implements HttpServlet {

	private final List<Object> controllers;

	public AnnotationServletV2(List<Object> controllers) {
		this.controllers = controllers;
	}

	@Override
	public void service(HttpRequest request, HttpResponse response) throws IOException {
		String path = request.getPath();

		for (Object controller : controllers) {
			Method[] methods = controller.getClass().getDeclaredMethods();
			for (Method method : methods) {
				if (method.isAnnotationPresent(Mapping.class)) {
					Mapping mapping = method.getAnnotation(Mapping.class);
					String value = mapping.value();
					if (value.equals(path)) {
						invoke(controller, method, request, response);
						return;
					}
				}
			}
		}
		throw new PageNotFoundException("request=" + path);
	}

	private void invoke(Object controller, Method method, HttpRequest request, HttpResponse response) {
		Class<?>[] parameterTypes = method.getParameterTypes();
		Object[] args = new Object[parameterTypes.length];

		for (int i = 0; i < parameterTypes.length; i++) {
			if (parameterTypes[i] == HttpRequest.class) {
				args[i] = request;
			} else if (parameterTypes[i] == HttpResponse.class) {
				args[i] = response;
			} else {
				throw new IllegalArgumentException("Invalid parameter type: " + parameterTypes[i]);
			}
		}
		try {
			method.invoke(controller, args);
		} catch (InvocationTargetException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
