package thread.bounded;

import java.util.ArrayDeque;
import java.util.Queue;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

/**
 * V1에서 발생한 문제를 해결하기 위해 반복문을 사용해서 스레드를 대기하는 방법을 적용했다.
 * 하지만 synchronized 임계 영역 안에서 락을 들고 대기하기 때문에, 다른 스레드가 임계 영역에 접근할 수 없는 문제 발생.
 * 결과적으로 나머지 스레드는 모두 BLOCKED 상태가 대기하게 되고 프로그램이 멈춰있는 상태가 된다.
 */
public class BoundedQueueV2 implements BoundedQueue {

    private final Queue<String> queue = new ArrayDeque<>(); // 공유 자원
    private final int max;

    public BoundedQueueV2(int max) {
        this.max = max;
    }

    @Override
    public synchronized void put(String data) {
        while (queue.size() == max) {
            log("[put] 큐가 가득 참, 생산자 대기: " + data);
            sleep(1000);
        }
        queue.offer(data);
    }

    @Override
    public synchronized String take() {
        while (queue.isEmpty()) {
            log("[take] 큐에 데이터가 없음, 소비자 대기");
            sleep(1000);
        }
        return queue.poll();
    }

    @Override
    public synchronized String toString() {
        return queue.toString();
    }
}
