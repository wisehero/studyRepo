package functional;

import java.util.function.Function;

public class SideEffectMain1 {

    public static int count = 0;

    public static void main(String[] args) {
        System.out.println("before count = " + count);

        Function<Integer, Integer> func = x -> {
            count++; // 여기서 전역 상태의 변수를 변경하므로 부수 효과가 발생
            return x * 2;
        };
        func.apply(10);
        System.out.println("after count = " + count);
    }
}
