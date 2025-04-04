package wisehero.springadvanced.templatemethodstrategy.trace.threadlocal;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import wisehero.springadvanced.templatemethodstrategy.trace.threadlocal.code.ThreadLocalService;

@Slf4j
public class ThreadLocalServiceTest {

	private ThreadLocalService sevice = new ThreadLocalService();

	@Test
	void threadLocal() {
		log.info("main start");
		Runnable userA = () -> {
			sevice.logic("userA");
		};

		Runnable userB = () -> {
			sevice.logic("userB");
		};

		Thread threadA = new Thread(userA);
		threadA.setName("thread-A");
		Thread threadB = new Thread(userB);
		threadB.setName("thread-B");

		threadA.start();
		sleep(100);
		threadB.start();

		sleep(2000);
		log.info("main exit");
	}

	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
