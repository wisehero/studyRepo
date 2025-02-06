package chap05.immutable;

public class Account {
    private double balance;

    public Account(int openingBalance) {
        this.balance = openingBalance;
    }

    public boolean rawWithdraw(int amount) {
        if (balance >= amount) {
            balance = balance - amount;
            return true;
        }
        return false;
    }

    public void rawDeposit(int amount) {
        balance = balance + amount;
    }

    public double getRawBalance() {
        return balance;
    }

    public boolean safeWithdraw(final int amount) {
        synchronized (this) {
            if (balance >= amount) {
                balance = balance - amount;
                return true;
            }
        }
        return false;
    }

    public void safeDeposit(final int amount) {
        synchronized (this) {
            balance = balance + amount;
        }
    }

    public double getSafeBalance() {
        synchronized (this) {
            return balance;
        }
    }

    public boolean withdraw(int amount, boolean safe) {
        if (safe) {
            return safeWithdraw(amount);
        } else {
            return rawWithdraw(amount);
        }
    }
}
