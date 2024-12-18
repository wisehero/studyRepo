package wisehero.springadvanced.templatemethodstrategy.app.v4;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import wisehero.springadvanced.templatemethodstrategy.trace.logtrace.LogTrace;
import wisehero.springadvanced.templatemethodstrategy.trace.template.AbstractTemplate;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV4 {

	private final LogTrace trace;

	public void save(String itemId) {
		AbstractTemplate<Void> template = new AbstractTemplate<Void>(trace) {
			@Override
			protected Void call() {
				if (itemId.equals("ex")) {
					throw new IllegalStateException("예외 발생!");
				}
				sleep(1000);
				return null;
			}
		};
		template.execute("OrderRepository.save()");
	}

	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
