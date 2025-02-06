package chap05;

public class FullSynchronizedObjectAccount {

    private double balance; // private 필드만 있다.

    public FullSynchronizedObjectAccount(double balance) {
        this.balance = balance; // 모든 필드가 생성자에서 초기화 된다.
    }

    // 아래의 모든 메서드는 동기화 되어 있다.
    public synchronized boolean withdraw(int amount) {
        if (balance >= amount) {
            balance = balance - amount;
        }

        return false;
    }

    public synchronized void deposit(int amount) {
        balance = balance + amount;
    }

    public synchronized double getBalance() {
        return balance;
    }
}
