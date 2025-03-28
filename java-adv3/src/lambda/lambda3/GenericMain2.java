package lambda.lambda3;

public class GenericMain2 {

	public static void main(String[] args) {
		ObjectFunction upperCase = s -> ((String)s).toUpperCase();
		Object result1 = upperCase.apply("hello");
		System.out.println("result1 = " + result1);

		ObjectFunction square = n -> (Integer)n * (Integer)n;
		Object result2 = square.apply(3);
		System.out.println("result2 = " + result2);
	}

	@FunctionalInterface
	interface ObjectFunction {
		Object apply(Object obj);
	}
}
