package thread.collection.simple;

import thread.collection.simple.list.BasicList;
import thread.collection.simple.list.SimpleList;
import thread.collection.simple.list.SyncList;

import static util.MyLogger.log;

public class SimpleListMainV2 {
    public static void main(String[] args) throws InterruptedException {
//        test(new BasicList());
        test(new SyncList());
    }


    private static void test(SimpleList list) throws InterruptedException {
        log(list.getClass().getSimpleName());

        Runnable addA = () -> {
            list.add("A");
            log("Thread-1: list.add(A)");
        };

        Runnable addB = () -> {
            list.add("B");
            log("Thread-2: list.add(B)");
        };

        Thread thread1 = new Thread(addA, "Thread-1");
        Thread thread2 = new Thread(addB, "Thread-2");

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        log(list);
    }
}
