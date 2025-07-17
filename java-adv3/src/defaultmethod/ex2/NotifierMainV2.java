package defaultmethod.ex2;

import java.util.List;

public class NotifierMainV2 {
    public static void main(String[] args) {
        List<Notifier> notifiers = List.of(
                new EmailNotifier(),
                new SNSNotifier(),
                new AppPushNotifier()
        );
        notifiers.forEach(n -> n.notify("서비스 가입을 환영합니다!"));
    }
}
