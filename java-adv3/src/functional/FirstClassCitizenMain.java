package functional;

import java.util.function.Function;

/**
 * 함수가 일반 값과 동일한 지위를 가진다.
 * 함수를 변수에 대입하거나, 다른 함수의 인자로 전달하거나, 함수에서 함수를 반환하는 고차 함수를 자유롭게 사용 가능.
 */
public class FirstClassCitizenMain {

    public static void main(String[] args) {

        // 함수를 변수에 담는다.
        Function<Integer, Integer> func = x -> x * 2;

        // 함수를 인자로 전달한다.
        applyFunction(10, func);

        // 함수를 반환한다.
        getFunc().apply(10);
    }

    // 고차 함수 : 함수를 인자로 받는다.
    public static Integer applyFunction(Integer input, Function<Integer, Integer> func) {
        return func.apply(input);
    }

    // 고차 함수 : 함수를 반환한다.
    public static Function<Integer, Integer> getFunc() {
        return x -> x * 2;
    }
}
