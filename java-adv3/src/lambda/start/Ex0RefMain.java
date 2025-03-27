package lambda.start;

public class Ex0RefMain {

	public static void hello() {
		System.out.println("프로그램 시작");

		// 변하는 부분 시작
		System.out.println("Hello Java");
		System.out.println("Hello Spring");
		// 변하는 부분 종료

		System.out.println("프로그램 종료");
	}

	public static void hello(String str) { // 값의 매개변수화
		System.out.println("프로그램 시작");
		System.out.println(str);
		System.out.println("프로그램 종료");
	}

	public static void main(String[] args) {
		hello("hello Java");
		hello("hello Spring");
	}
}
