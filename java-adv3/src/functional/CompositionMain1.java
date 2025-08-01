package functional;

import java.util.function.Function;

/**
 * 간단한 함수를 조합해 더 복잡한 함수를 만드는 것이 좋다.
 * 작은 단위의 함수들을 체이닝하거나 파이프라이닝 해서 재사용성을 높이고 코드 이해도를 높인다.
 */
public class CompositionMain1 {
    public static void main(String[] args) {
        Function<Integer, Integer> square = x -> x * x;
        Function<Integer, Integer> add = x -> x + 1;

        // 함수 합성
        // 1. compose()를 사용한 새로운 함수 생성, add 적용 후 square 적용하는 newFunc1 생성
        Function<Integer, Integer> newFunc1 = square.compose(add);
        System.out.println("newFunc1 결과: " + newFunc1.apply(2));

        // 2. andThen()을 사용한 새로운 함수 생성
        // 먼저 square 적용 후 add 적용하는 새로운 함수 newFunc2 생성
        Function<Integer, Integer> newFunc2 = add.compose(square);
        System.out.println("newFunc2 결과: " + newFunc2.apply(2));
    }
}
