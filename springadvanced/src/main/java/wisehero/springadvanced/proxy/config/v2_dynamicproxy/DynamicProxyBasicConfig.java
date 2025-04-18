package wisehero.springadvanced.proxy.config.v2_dynamicproxy;

import java.lang.reflect.Proxy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import wisehero.springadvanced.proxy.app.v1.OrderControllerV1;
import wisehero.springadvanced.proxy.app.v1.OrderControllerV1Impl;
import wisehero.springadvanced.proxy.app.v1.OrderRepositoryV1;
import wisehero.springadvanced.proxy.app.v1.OrderRepositoryV1Impl;
import wisehero.springadvanced.proxy.app.v1.OrderServiceV1;
import wisehero.springadvanced.proxy.app.v1.OrderServiceV1Impl;
import wisehero.springadvanced.proxy.config.v2_dynamicproxy.handler.LogTraceBasicHandler;
import wisehero.springadvanced.proxy.trace.logtrace.LogTrace;

@Configuration
public class DynamicProxyBasicConfig {

	@Bean
	public OrderControllerV1 orderControllerV1(LogTrace logTrace) {
		OrderControllerV1 orderControllerV1 = new OrderControllerV1Impl(orderServiceV1(logTrace));
		OrderControllerV1 proxy = (OrderControllerV1)Proxy.newProxyInstance(OrderControllerV1.class.getClassLoader(),
			new Class[] {OrderControllerV1.class},
			new LogTraceBasicHandler(orderControllerV1, logTrace));
		return proxy;
	}

	@Bean
	public OrderServiceV1 orderServiceV1(LogTrace logTrace) {
		OrderServiceV1 orderServiceV1 = new OrderServiceV1Impl(orderRepositoryV1(logTrace));
		OrderServiceV1 proxy = (OrderServiceV1)Proxy.newProxyInstance(OrderServiceV1.class.getClassLoader(),
			new Class[] {OrderServiceV1.class},
			new LogTraceBasicHandler(orderServiceV1, logTrace));
		return proxy;
	}

	@Bean
	public OrderRepositoryV1 orderRepositoryV1(LogTrace logTrace) {
		OrderRepositoryV1 orderRepository = new OrderRepositoryV1Impl();

		OrderRepositoryV1 proxy = (OrderRepositoryV1)Proxy.newProxyInstance(OrderRepositoryV1.class.getClassLoader(),
			new Class[] {OrderRepositoryV1.class},
			new LogTraceBasicHandler(orderRepository, logTrace));
		return proxy;
	}
}
