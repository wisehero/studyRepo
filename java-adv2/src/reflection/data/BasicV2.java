package reflection.data;

import java.lang.reflect.Modifier;
import java.util.Arrays;

public class BasicV2 {

	public static void main(String[] args) throws ClassNotFoundException {
		Class<BasicData> basicData = BasicData.class;

		System.out.println("basicData.getName() = " + basicData.getName()); // refliection.data.BasicData
		System.out.println("basicData.getSimpleName() = " + basicData.getSimpleName()); // BasicData
		System.out.println("basicData.getPackage() = " + basicData.getPackage()); // refelction.data

		System.out.println("basicData.getSuperclass() = " + basicData.getSuperclass()); // class java.lang.Object
		System.out.println("basicData.getInterfaces() = " + Arrays.toString(basicData.getInterfaces())); // []

		System.out.println("basicData.isInterface() = " + basicData.isInterface()); // false
		System.out.println("basicData.isEnum() = " + basicData.isEnum()); // false
		System.out.println("basicData.isAnnotations() = " + basicData.isAnnotation()); // false

		int modifiers = basicData.getModifiers();
		System.out.println("basicData.getModifiers() = " + modifiers); // 1
		System.out.println("isPublic = " + Modifier.isPublic(modifiers)); // true
		System.out.println("Modifier.toString() = " + Modifier.toString(modifiers)); // public
	}
}
