package defaultmethod.ex1;

public class SNSNotifier implements Notifier {

    @Override
    public void notify(String message) {
        System.out.println("[SNS]" + message);
    }
}
