package lambda.lambda5;

import static lambda.lambda5.map.GenericMapper.*;

import java.util.List;

import lambda.lambda5.map.GenericMapper;

public class MapMainV5 {

	public static void main(String[] args) {
		// String -> String
		List<String> fruits = List.of("apple", "banana", "orange");
		List<String> upperFruits = map(fruits, fr -> fr.toUpperCase());
		System.out.println(upperFruits);

		// String -> Integer
		List<Integer> lengths = map(fruits, fr -> fr.length());
		System.out.println(lengths);

		// Integer -> String
		List<Integer> integers = List.of(1, 2, 3);
		List<String> starList = GenericMapper.map(integers, n -> "*".repeat(n));
		System.out.println(starList);
	}
}
