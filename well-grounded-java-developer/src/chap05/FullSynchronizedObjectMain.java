package chap05;

public class FullSynchronizedObjectMain {
    private static final int MAX_TRANSFERS = 1_000;

    public static void main(String[] args) throws InterruptedException {
        FullSynchronizedObjectAccount a = new FullSynchronizedObjectAccount(10_000);
        FullSynchronizedObjectAccount b = new FullSynchronizedObjectAccount(10_000);

        Thread threadA = new Thread(() -> {
            for (int i = 0; i < MAX_TRANSFERS; i++) {
                boolean ok = a.transferTo(b, 1);
                if (!ok) {
                    System.out.println("Thread A failed at " + i);
                }
            }
        });

        Thread threadB = new Thread(() -> {
            for (int i = 0; i < MAX_TRANSFERS; i++) {
                boolean ok = b.transferTo(a, 1);
                if (!ok) {
                    System.out.println("Thread B failed at " + i);
                }
            }
        });

        threadA.start();
        threadB.start();
        threadA.join();
        threadB.join();

        System.out.println("End : " + a.getBalance() + " : " + b.getBalance());
    }
}
