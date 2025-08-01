package functional;

import java.util.function.Function;

public class SideEffectMain2 {
    public static void main(String[] args) {
        Function<Integer, Integer> func = (x) -> {
            int result = x * 2;

            System.out.println("x = " + x + ", result = " + (x * 2)); // 외부 세계(콘솔)에 영향을 주므로 부수 효과 발생.
            return result;
        };
        func.apply(10);
    }
}
