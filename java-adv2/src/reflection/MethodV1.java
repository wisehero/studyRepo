package reflection;

import java.lang.reflect.Method;

import reflection.data.BasicData;

public class MethodV1 {

	public static void main(String[] args) {
		Class<BasicData> helloClass = BasicData.class;

		System.out.println("============== methods() ==============");
		Method[] methods = helloClass.getMethods(); // 모든 Public 메서드 반환(상속받은 클래스의 것도)

		for (Method method : methods) {
			System.out.println("method = " + method);
		}

		System.out.println("============== declaredMethods() ==============");
		Method[] declaredMethods = helloClass.getDeclaredMethods(); // 모든 메서드 반환
		for (Method declaredMethod : declaredMethods) {
			System.out.println("declaredMethod = " + declaredMethod);
		}
	}
}
