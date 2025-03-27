package lambda.lambda2;

import lambda.MyFunction;

public class LambdaPassMain3 {

	public static void main(String[] args) {
		MyFunction add = getOperations("add");
		System.out.println("add.apply(1, 2) = " + add.apply(1, 2));

		MyFunction sub = getOperations("sub");
		System.out.println("sub.apply(1, 2) = " + sub.apply(1, 2));

		MyFunction xxx = getOperations("xxx");
		System.out.println("xxx.apply(1, 2) = " + xxx.apply(1, 2));
	}

	static MyFunction getOperations(String operator) {
		switch (operator) {
			case "add":
				return (a, b) -> a + b;
			case "sub":
				return (a, b) -> a - b;
			default:
				return (a, b) -> 0;
		}
	}
}
