package thread.control.interrupt;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class ThreadStopMainV1 {
    public static void main(String[] args) {
        MyTask task = new MyTask();
        Thread thread = new Thread(task, "work");
        thread.start();

        sleep(4000);
        log("작업 중단 지시 runFlag=false");
        task.runFlag = false;
    }

    static class MyTask implements Runnable {
        volatile boolean runFlag = true; // volatile은 여러 스레드가 공유하는 값에 사용하는 키워드다.

        @Override
        public void run() {
            while (runFlag) {
                log("작업 중");
                sleep(3000);
            }
            log("자원 정리"); // 2초 정도 경과후에 이 로그를 남기는 게 실행됨. 즉, 즉각 반응하지 않음
            log("작업 종료");
        }
    }
}
