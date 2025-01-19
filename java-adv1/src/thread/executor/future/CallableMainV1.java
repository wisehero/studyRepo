package thread.executor.future;

import static util.MyLogger.*;
import static util.ThreadUtils.*;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableMainV1 {

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		ExecutorService es = Executors.newFixedThreadPool(1);
		Future<Integer> future = es.submit(new MyCallable());
		Integer result = future.get(); // get() 메서드를 호출하면 Callable의 call() 메서드가 실행되고 리턴값을 가져온다.
		log("result value = " + result);
		es.close();
	}

	static class MyCallable implements Callable<Integer> {
		@Override
		public Integer call() throws Exception {
			log("Callable 시작");
			sleep(2000);
			int value = new Random().nextInt(10);
			log("create value = " + value);
			log("Callable 완료");
			return value; // 이제 return 값을 줄 수 있다.
		}
	}
}
