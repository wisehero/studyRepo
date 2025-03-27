package lambda.start;

import java.util.Random;

import lambda.Procedure;

public class Ex1RefMainV2 {

	public static void hello(Procedure procedure) {
		long startNs = System.nanoTime();

		// 코드 조각 시작
		procedure.run();
		// 코드 조각 종료

		long endNs = System.nanoTime();
		System.out.println("실행 시간: " + (endNs - startNs) + "ns");
	}

	public static void main(String[] args) {
		Procedure dice = new Procedure() {
			@Override
			public void run() {
				int randomValue = new Random().nextInt(6) + 1;
				System.out.println("주사위 = " + randomValue);
			}
		};

		Procedure sum = new Procedure() {
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					System.out.println("i = " + i);
				}
			}
		};

		hello(dice);
		hello(sum);
	}
}
