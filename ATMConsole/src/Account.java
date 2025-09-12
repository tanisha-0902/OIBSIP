import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Account {
    private String userId;
    private String pin;
    private double balance;
    private List<Transaction> history =
            Collections.synchronizedList(new ArrayList<>());

    public Account(String userId, String pin, double initialBalance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = initialBalance;
    }

    public String getUserId() {
        return userId;
    }

    public boolean verifyPin(String inputPin) {
        return this.pin.equals(inputPin);
    }

    public synchronized double getBalance() {
        return balance;
    }

    public synchronized void deposit(double amount) {
        if (amount <= 0) return;
        balance += amount;
        history.add(new Transaction(TransactionType.DEPOSIT, amount, balance, null));
    }

    public synchronized boolean withdraw(double amount) {
        if (amount <= 0 || amount > balance) return false;
        balance -= amount;
        history.add(new Transaction(TransactionType.WITHDRAW, amount, balance, null));
        return true;
    }

    public boolean transferTo(Account to, double amount) {
        if (to == null) return false;

        // Lock ordering to avoid deadlocks
        Account first = this;
        Account second = to;
        if (System.identityHashCode(first) > System.identityHashCode(second)) {
            first = to;
            second = this;
        }

        synchronized (first) {
            synchronized (second) {
                if (amount <= 0 || amount > this.balance) return false;
                this.balance -= amount;
                this.history.add(new Transaction(TransactionType.TRANSFER_OUT,
                        amount, this.balance, "To: " + to.getUserId()));

                to.balance += amount;
                to.history.add(new Transaction(TransactionType.TRANSFER_IN,
                        amount, to.balance, "From: " + this.getUserId()));
                return true;
            }
        }
    }

    public List<Transaction> getHistory() {
        return history;
    }
}
