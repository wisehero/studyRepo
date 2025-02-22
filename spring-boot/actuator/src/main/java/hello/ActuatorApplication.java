package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import hello.order.gauge.StockConfigV1;
import hello.order.v0.OrderConfigV0;
import hello.order.v1.OrderConfigV1;
import hello.order.v2.OrderConfigV2;
import hello.order.v2.OrderServiceV2;
import hello.order.v3.OrderConfigV3;
import hello.order.v4.OrderConfigV4;

// @Import(OrderConfigV0.class)
// @Import(OrderConfigV1.class)
// @Import(OrderConfigV2.class)
// @Import(OrderConfigV3.class)
@Import({OrderConfigV4.class, StockConfigV1.class})
@SpringBootApplication(scanBasePackages = "hello.controller")
public class ActuatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActuatorApplication.class, args);
	}

}
