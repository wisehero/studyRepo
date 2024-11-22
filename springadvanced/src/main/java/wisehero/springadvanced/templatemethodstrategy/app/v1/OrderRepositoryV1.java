package wisehero.springadvanced.templatemethodstrategy.app.v1;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import wisehero.springadvanced.templatemethodstrategy.trace.TraceStatus;
import wisehero.springadvanced.templatemethodstrategy.trace.hellotrace.HelloTraceV1;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV1 {

	private final HelloTraceV1 trace;

	public void save(String itemId) {

		TraceStatus status = null;
		try {
			status = trace.begin("OrderRepository.save()");

			// 저장 로직
			if (itemId.equals("ex")) {
				throw new IllegalStateException("예외 발생");
			}
			sleep(1000);
			trace.end(status);
		} catch (Exception e) {
			trace.exception(status, e);
			throw e;
		}
	}

	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}