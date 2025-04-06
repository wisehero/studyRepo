package methodref;

public class Person {

	private String name;

	// 생성자
	public Person() {
		this("Unknown");
	}

	// 생성자, 매개변수
	public Person(String name) {
		this.name = name;
	}

	// 정적 메서드
	public static String greeting() {
		return "Hello";
	}

	// 정적 메서드, 매개변수
	public static String greeting(String name) {
		return "Hello, " + name;
	}

	public static String greetingWithName(String name) {
		return "Hello, " + name;
	}

	public String getName() {
		return name;
	}

	public String introduce() {
		return "I am " + name;
	}

	public String introduceWithNumber(int number) {
		return "I am " + name + ", my number is " + number;
	}
}
