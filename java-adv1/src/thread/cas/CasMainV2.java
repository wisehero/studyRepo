package thread.cas;

import java.util.concurrent.atomic.AtomicInteger;

import static util.MyLogger.log;

public class CasMainV2 {
    public static void main(String[] args) {
        AtomicInteger atomicIntger = new AtomicInteger(0);
        System.out.println("start value = " + atomicIntger.get());

        // incrementAndGet 구현
        int resultValue1 = incrementAndGet(atomicIntger);
        System.out.println("resultValue1 = " + resultValue1);

        int resultValue2 = incrementAndGet(atomicIntger);
        System.out.println("resultValue2 = " + resultValue2);
    }

    private static int incrementAndGet(AtomicInteger atomicInteger) {
        int getValue;
        boolean result;
        do {
            getValue = atomicInteger.get(); // 값을 읽어온다.
            log("getValue = " + getValue);
            result = atomicInteger.compareAndSet(getValue, getValue + 1); // 방금 읽은 value의 값이 메모리의 value와 같다면 1 증가
            log("result = " + result);
        } while (!result); // result가 false라면 다시 시도
        return getValue + 1;
    }
}
