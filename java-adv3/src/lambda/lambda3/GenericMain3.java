package lambda.lambda3;

public class GenericMain3 {

	public static void main(String[] args) {
		ObjectFunction uppercase = new ObjectFunction() {
			@Override
			public Object apply(Object obj) {
				return ((String)obj).toUpperCase();
			}
		};
		String result1 = (String)uppercase.apply("hello");
		System.out.println("result1 = " + result1);

		ObjectFunction square = new ObjectFunction() {
			@Override
			public Object apply(Object n) {
				return (Integer)n * (Integer)n;
			}
		};
		Integer result2 = (Integer)square.apply(3);
		System.out.println("result2 = " + result2);
	}

	@FunctionalInterface
	interface ObjectFunction {
		Object apply(Object ojb);
	}
}


