package methodref;

import java.util.function.Function;
import java.util.function.Supplier;

public class MethodRefEx2 {

	public static void main(String[] args) {
		// 1. 매개변수가 있는 메서드 참조
		Function<String, String> staticMethod1 = (name) -> Person.greetingWithName(name);
		Function<String, String> staticMethod2 = Person::greetingWithName;

		System.out.println("staticMethod1 = " + staticMethod1.apply("John"));
		System.out.println("staticMethod2 = " + staticMethod2.apply("John"));

		// 2. 특정 객체의 인스턴스 참조
		Person person = new Person("Kim");
		Supplier<String> instanceMethod1 = () -> person.introduce();
		Supplier<String> instanceMethod2 = person::introduce;

		System.out.println("instanceMethod1 = " + instanceMethod1.get());
		System.out.println("instanceMethod2 = " + instanceMethod2.get());

		// 3. 생성자 참조
		Function<String, Person> supplier1 = name -> new Person(name);
		Function<String, Person> supplier2 = Person::new;

		System.out.println("supplier1 = " + supplier1.apply("Lee"));
		System.out.println("supplier2 = " + supplier2.apply("Kim"));
	}
}
