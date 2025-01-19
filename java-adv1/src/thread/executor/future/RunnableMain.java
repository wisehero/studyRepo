package thread.executor.future;

import static util.MyLogger.*;
import static util.ThreadUtils.*;

import java.util.Random;

public class RunnableMain {

	public static void main(String[] args) throws InterruptedException {
		MyRunnable task = new MyRunnable();
		Thread thread = new Thread(task, "Thread-1");
		thread.start();
		thread.join(); // 값을 가져오기 위해 기다림
		int result = task.value;
		log("result value = " + result);
	}

	static class MyRunnable implements Runnable {

		int value;

		@Override
		public void run() {
			log("Runnable 시작");
			sleep(2000); // 작업에 2초가 걸린다고 가정
			value = new Random().nextInt(10);
			log("create value = " + value);
			log("Runnable 완료");
			System.out.println("MyRunnable");
		}
	}
}
