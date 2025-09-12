import java.util.InputMismatchException;
import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {
        Bank bank = new Bank();
        Scanner sc = new Scanner(System.in);

        System.out.println("================ Welcome to SimpleATM ================");
        System.out.print("Enter User ID: ");
        String userId = sc.nextLine().trim();
        System.out.print("Enter PIN: ");
        String pin = sc.nextLine().trim();

        Account user = bank.authenticate(userId, pin);
        if (user == null) {
            System.out.println("\nInvalid credentials. Exiting application.");
            sc.close();
            return;
        }

        System.out.println("\nLogin successful! Welcome, " + user.getUserId());

        while (true) {
            System.out.println("\n--------- MENU ---------");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Choose an option (1-5): ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.println("\n--- Transaction History ---");
                    if (user.getHistory().isEmpty()) {
                        System.out.println("No transactions yet.");
                    } else {
                        user.getHistory().forEach(System.out::println);
                    }
                    break;

                case "2":
                    double withdrawAmount = readPositiveDouble(sc, "Enter amount to withdraw: ");
                    if (user.withdraw(withdrawAmount)) {
                        System.out.printf("Withdrawn %.2f. New balance: %.2f%n",
                                withdrawAmount, user.getBalance());
                    } else {
                        System.out.println("Insufficient balance or invalid amount.");
                    }
                    break;

                case "3":
                    double depositAmount = readPositiveDouble(sc, "Enter amount to deposit: ");
                    user.deposit(depositAmount);
                    System.out.printf("Deposited %.2f. New balance: %.2f%n",
                            depositAmount, user.getBalance());
                    break;

                case "4":
                    System.out.print("Enter target User ID: ");
                    String targetId = sc.nextLine().trim();
                    Account target = bank.getAccount(targetId);
                    if (target == null) {
                        System.out.println("Target account not found.");
                        break;
                    }
                    double transferAmount = readPositiveDouble(sc, "Enter amount to transfer: ");
                    if (user.transferTo(target, transferAmount)) {
                        System.out.printf("Transferred %.2f to %s. New balance: %.2f%n",
                                transferAmount, target.getUserId(), user.getBalance());
                    } else {
                        System.out.println("Transfer failed (check balance/amount).");
                    }
                    break;

                case "5":
                    System.out.println("Thank you for using SimpleATM. Goodbye!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please select 1-5.");
            }
        }
    }

    /**
     * Reads a positive double from the user with validation.
     */
    private static double readPositiveDouble(Scanner sc, String prompt) {
        double value = -1;
        while (value <= 0) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                value = Double.parseDouble(input);
                if (value <= 0) {
                    System.out.println("Amount must be greater than 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a valid amount.");
            }
        }
        return value;
    }
}
