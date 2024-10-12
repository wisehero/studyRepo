package wisehero.springadvanced.proxy.app.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface OrderControllerV1 {

	@GetMapping("/v1/request")
	String request(@RequestParam("itemId") String itemId); // LogTrace 적용 대상

	@GetMapping("/v1/no-log")
	String noLog(); // LogTrace 적용대상 아님.
}
