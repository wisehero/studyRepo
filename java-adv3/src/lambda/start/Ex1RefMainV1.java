package lambda.start;

import lambda.Procedure;

public class Ex1RefMainV1 {

	public static void hello(Procedure procedure) {
		long startNs = System.nanoTime();

		// 코드 조각 시작
		procedure.run();
		// 코드 조각 종료

		long endNs = System.nanoTime();
		System.out.println("실행 시간 : " + (endNs - startNs) + "ns");
	}

	static class Dice implements Procedure {
		@Override
		public void run() {
			for (int i = 1; i <= 3; i++) {
				System.out.println("i = " + i);
			}
		}
	}

	static class Sum implements Procedure {
		@Override
		public void run() {
			for (int i = 1; i <= 3; i++) {
				System.out.println("i = " + i);
			}
		}
	}

	public static void main(String[] args) {
		Procedure dice = new Dice();
		Procedure sum = new Sum();

		hello(dice);
		hello(sum);
	}
}
