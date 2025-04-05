package lambda.lambda5;

import java.util.List;

import lambda.lambda5.ex.Student;
import lambda.lambda5.stream.MyStreamV3;

public class MyStreamV3Main {

	public static void main(String[] args) {
		List<Student> students = List.of(
			new Student("홍길동", 90),
			new Student("이순신", 80),
			new Student("강감찬", 70),
			new Student("유관순", 60),
			new Student("안중근", 50)
		);

		List<String> result1 = ex1(students);
		System.out.println("result1 = " + result1);

		List<String> result2 = ex2(students);
		System.out.println("result2 = " + result2);
	}

	static List<String> ex1(List<Student> students) {
		return MyStreamV3.of(students)
			.filter(s -> s.getScore() >= 80)
			.map(s -> s.getName())
			.toList();
	}

	static List<String> ex2(List<Student> students) {
		return MyStreamV3.of(students)
			.filter(s -> s.getScore() >= 80)
			.filter(s -> s.getName().length() == 5)
			.map(s -> s.getName())
			.map(name -> name.toUpperCase())
			.toList();
	}
}
