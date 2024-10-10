package wisehero.springadvanced.proxy;

import lombok.extern.slf4j.Slf4j;
import wisehero.springadvanced.proxy.code.Subject;

@Slf4j
public class RealSubject implements Subject {

	@Override
	public String operation() {
		log.info("실제 객체 호출");
		sleep(1000); // 큰 데이터를 조회한다고 가정.

		return "data";
	}

	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
