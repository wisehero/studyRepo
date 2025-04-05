package lambda.lambda6;

public class CaptureMain {

	public static void main(String[] args) {
		final int final1 = 10; // 명시적 final
		int final2 = 20; // 사실상 final
		int changedVar = 30; // 변하는 변수

		// 1. 익명 클래스의 캡쳐
		Runnable anonymous = new Runnable() {
			@Override
			public void run() {
				System.out.println("익명 클래스 final1 = " + final1);
				System.out.println("익명 클래스 final2 = " + final2);
				// System.out.println("익명 클래스 changedVar = " + changedVar);
			}
		};

		// 2. 람다 표현식에서의 캡쳐
		Runnable lambda = () -> {
			System.out.println("람다 final1 = " + final1);
			System.out.println("람다 final2 = " + final2);
			// System.out.println("람다 changedVar = " + changedVar); // 에러 발생
		};

		changedVar++;

		anonymous.run();
		lambda.run();
	}
}
