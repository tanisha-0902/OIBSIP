import java.util.HashMap;
import java.util.Map;

public class Bank {
    private Map<String, Account> accounts = new HashMap<>();

    public Bank() {
        // Demo accounts
        accounts.put("1001", new Account("1001", "1234", 5000.00));
        accounts.put("1002", new Account("1002", "2345", 3000.00));
        accounts.put("1003", new Account("1003", "3456", 10000.00));
    }

    public Account authenticate(String userId, String pin) {
        Account acc = accounts.get(userId);
        if (acc != null && acc.verifyPin(pin)) {
            return acc;
        }
        return null;
    }

    public Account getAccount(String userId) {
        return accounts.get(userId);
    }
}
