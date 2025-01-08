package thread.control;

import static util.MyLogger.log;

public class ThreadStateMain {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new MyRunnable());
        log("myThread.state1 = " + thread.getState()); // 여기서는 NEW 상태다.
        log("myThread.start()");
        thread.start();

        Thread.sleep(1000);
        log("myThread.state3 = " + thread.getState()); // 여기서는 TIME_WAITING 상태다.
        Thread.sleep(4000);
        log("myThread.state5 = " + thread.getState()); // 여기서는 TERMINATED 상태다.
        log("end");
    }

    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            try {
                log("start");
                log("myThread.state2 = " + Thread.currentThread().getState()); // 여기서는 RUNNABLE 상태다.
                log("sleep() start");
                Thread.sleep(3000);
                log("sleep() end");
                log("myThread.state4 = " + Thread.currentThread().getState());
                log("end");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
