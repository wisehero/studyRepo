package thread.bounded;

import java.util.ArrayDeque;
import java.util.Queue;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

/**
 * synchronized와 함께 사용할 수 있는 wait(), notify(), notifyAll()을 사용해서 문제를 해결.
 * 스레드를 제어하는 큐 자료구조가 완성. 하지만 스레드 대기 집합이 하나이기 때문에 원하는 스레드를 선택해서 깨울 수
 * 없다는 문제가 있다. 예를 들어서 생산자는 데이터를 생산한 다음 대기하는 소비자를 깨워야 하는데, 대기하는 생산자를
 * 깨울 수 있다.
 */
public class BoundedQueueV3 implements BoundedQueue {

    private final Queue<String> queue = new ArrayDeque<>(); // 공유 자원
    private final int max;

    public BoundedQueueV3(int max) {
        this.max = max;
    }

    @Override
    public synchronized void put(String data) {
        while (queue.size() == max) {
            log("[put] 큐가 가득 참, 생산자 대기");
            try {
                wait();
                log("[put] 생산자 깨어남");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        queue.offer(data);
        log("[put] 생산자 데이터 저장, notify() 호출");
        notify();
    }

    @Override
    public synchronized String take() {
        while (queue.isEmpty()) {
            log("[take] 큐에 데이터가 없음, 소비자 대기");
            try {
                wait();
                log("[take] 소비자 깨어남");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        String data = queue.poll();
        log("[take] 소비자 데이터 획득, notify() 호출");
        notify();
        return data;
    }

    @Override
    public synchronized String toString() {
        return queue.toString();
    }
}
