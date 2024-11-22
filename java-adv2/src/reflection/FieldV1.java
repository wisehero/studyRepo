package reflection;

import java.lang.reflect.Field;

import reflection.data.BasicData;

public class FieldV1 {

	public static void main(String[] args) {
		Class<BasicData> helloClass = BasicData.class;

		System.out.println("============== fields() ==============");
		Field[] fields = helloClass.getFields(); // 모든 Public 필드 반환(상속받은 클래스의 것도)
		for (Field field : fields) {
			System.out.println("field = " + field);
		}

		System.out.println("============== declaredFields() ==============");
		Field[] declaredFields = helloClass.getDeclaredFields(); // 모든 필드 반환
		for (Field declaredField : declaredFields) {
			System.out.println("declaredField = " + declaredField);
		}
	}
}
