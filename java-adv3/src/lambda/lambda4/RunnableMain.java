package lambda.lambda4;

public class RunnableMain {

	public static void main(String[] args) {
		Runnable runnable1 = new Runnable() {
			@Override
			public void run() {
				System.out.println("runnable1");
			}
		};
		runnable1.run();

		Runnable runnable2 = () -> System.out.println("runnable2");
		runnable2.run();
	}
}
