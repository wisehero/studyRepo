package defaultmethod.ex2;

import java.time.LocalDateTime;

public interface Notifier {

    void notify(String message);

//    void scheduleNotification(String message, LocalDateTime scheduledTime);

    default void scheduleNotification(String message, LocalDateTime scheduledAt) {
        System.out.println("기본 스케줄링 message: " + message + ", time: " + scheduledAt);
    }
}
