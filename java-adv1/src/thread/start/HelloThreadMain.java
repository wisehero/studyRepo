package thread.start;

public class HelloThreadMain {

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + ": main() start");

        HelloThread helloThread = new HelloThread();
        System.out.println(Thread.currentThread().getName() + ": start() 호출전");

        helloThread.start(); // 스레드를 실행하는 메서드. start()가 실행된 후 내부의 run()이 실행된다.
        System.out.println(Thread.currentThread().getName() + ": start() 호출후");

        System.out.println(Thread.currentThread().getName() + ": main() end");


        /**
         * 1. main 스레드가 HelloThread 인스턴스를 생성한다.
         * 2. start() 메서드를 호출하면 Thread-0 스레드가 시작되면서 run() 메서드를 호출한다.
         * 여기서 스레드 간의 실행 순서는 보장되지 않는다.
         */
    }
}
