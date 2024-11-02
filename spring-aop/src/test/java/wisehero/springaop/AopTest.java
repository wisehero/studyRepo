package wisehero.springaop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import lombok.extern.slf4j.Slf4j;
import wisehero.springaop.order.OrderRepository;
import wisehero.springaop.order.OrderService;
import wisehero.springaop.order.aop.AspectV6Advice;

@Slf4j
// @Import({AspectV5Order.LogAspect.class, AspectV5Order.TxAspect.class})
@Import(AspectV6Advice.class)
@SpringBootTest
public class AopTest {

	@Autowired
	OrderService orderService;

	@Autowired
	OrderRepository orderRepository;

	@Test
	void aopInfo() {
		log.info("isAopProxy, orderService={}", AopUtils.isAopProxy(orderService));
		log.info("isAopProxy, orderRepository={}", AopUtils.isAopProxy(orderRepository));
	}

	@Test
	void success() {
		orderService.orderItem("itemA");
	}

	@Test
	void exception() {
		Assertions.assertThatThrownBy(() -> orderService.orderItem("ex"))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("예외 발생!");
	}
}
