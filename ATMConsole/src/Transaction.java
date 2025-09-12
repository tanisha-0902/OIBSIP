import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private TransactionType type;
    private double amount;
    private LocalDateTime timestamp;
    private double balanceAfter;
    private String note;

    public Transaction(TransactionType type, double amount, double balanceAfter, String note) {
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        this.balanceAfter = balanceAfter;
        this.note = note;
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("[%s] %s  Amount: %.2f  Balance: %.2f  %s",
                timestamp.format(fmt),
                type,
                amount,
                balanceAfter,
                (note == null ? "" : "Note: " + note));
    }
}
