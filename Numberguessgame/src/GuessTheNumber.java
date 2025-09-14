import javax.swing.JOptionPane;
import java.util.Random;

public class GuessTheNumber {
    public static void main(String[] args) {
        Random random = new Random();
        int rounds = 3;              // number of rounds to play
        int totalScore = 0;          // final score after all rounds

        JOptionPane.showMessageDialog(null,
                "Welcome to Guess the Number Game!\n" +
                        "You will play " + rounds + " rounds.\n" +
                        "Try to guess the number between 1 and 100.\n" +
                        "You will get points depending on how quickly you guess."
        );

        for (int r = 1; r <= rounds; r++) {
            int number = random.nextInt(100) + 1;  // computer’s number
            int attempts = 0;
            int maxAttempts = 10;
            int points = 100;
            boolean found = false;

            JOptionPane.showMessageDialog(null,
                    "Round " + r + " started!\n" +
                            "I have selected a number between 1 and 100.\n" +
                            "You have " + maxAttempts + " chances to guess it."
            );

            while (attempts < maxAttempts) {
                String guessStr = JOptionPane.showInputDialog(
                        "Round " + r + "\nAttempt " + (attempts + 1) +
                                ": Enter your guess"
                );

                if (guessStr == null) {
                    JOptionPane.showMessageDialog(null, "Game closed by user.");
                    return; // exit the whole program
                }

                int guess;
                try {
                    guess = Integer.parseInt(guessStr.trim());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a number.");
                    continue; // don’t count invalid input as an attempt
                }

                attempts++;

                if (guess == number) {
                    // subtract 10 points for each extra attempt, minimum 10
                    points = Math.max(100 - (attempts - 1) * 10, 10);
                    totalScore += points;

                    JOptionPane.showMessageDialog(null,
                            "Correct! You guessed it in " + attempts + " tries.\n" +
                                    "Points scored in this round: " + points
                    );
                    found = true;
                    break;
                } else if (guess < number) {
                    JOptionPane.showMessageDialog(null, "Too low! Try again.");
                } else {
                    JOptionPane.showMessageDialog(null, "Too high! Try again.");
                }
            }

            if (!found) {
                JOptionPane.showMessageDialog(null,
                        "You ran out of chances! The number was " + number
                );
            }
        }

        JOptionPane.showMessageDialog(null,
                "Game Over!\nYour total score is: " + totalScore
        );
    }
}
