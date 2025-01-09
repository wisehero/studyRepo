package thread.control.printer;

import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

// q를 눌렀을 경우 바로 종료가 되지 않는 문제를 가지고 있음.
public class MyPrinterV1 {
    public static void main(String[] args) {
        Printer printer = new Printer();
        Thread printerThread = new Thread(printer, "printer");
        printerThread.start();

        Scanner userInput = new Scanner(System.in);
        while (true) {
            log("프린터할 문서를 입력하세요. 종료 (q): ");
            String input = userInput.nextLine();
            if (input.equals("q")) {
                printer.work = false;
                break;
            }

            printer.addJob(input);
        }
    }

    static class Printer implements Runnable {
        volatile boolean work = true;
        Queue<String> jobQueue = new ConcurrentLinkedQueue<>(); // 여러 스레드가 동시에 접근하는 경우 동시성 컬렉션 사용

        @Override
        public void run() {
            while (work) {
                if (jobQueue.isEmpty()) {
                    continue;
                }

                String job = jobQueue.poll();
                log("출력 시작: " + job + ", 대기 문서: " + jobQueue);
                sleep(3000);
                log("출력 완료: " + job);
            }
        }

        public void addJob(String input) {
            jobQueue.offer(input);
        }
    }
}
