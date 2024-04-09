import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private int answer;
    private int attempts;
    private ArrayList<Integer> guesses;
    private Difficulty difficulty;
    private static final Scanner scanner = new Scanner(System.in);
    private Random rand = new Random();
    private static boolean debuggerMode = false;

    private static final String[] HIGHER_HINTS = {
        "You got to think bigger than that!",
        "Aim higher, you're on the right track!",
        "Reach for the stars!",
        "Elevate your thought",
        "Ascend to new heights with your guess!",
        "Your guess could use a little boost.",
        "Imagine you're climbing a ladder; you need to go up.",
        "If this were temperature, we'd be looking for a warmer figure.",
        "It's like limbo, but the opposite; you want to go over, not under."
    };

    private static final String[] LOWER_HINTS = {
        "Cool it with the guesses, you're flying too high!",
        "Sometimes less is more.",
        "The valley might be where the treasure lies.",
        "If this were a limbo bar, you'd want to go under it.",
        "Think about taking a step down to the solution.",
        "Gravity is a hint: what goes up, must come down.",
        "If numbers were volume, you'd want to turn it down a notch.",
        "Aim closer to the ground, you're soaring sky high!",
        "There's wisdom in the depths, you know."
    };

    public static void main(String[] args) {
        try {
            showMainMenu();
        } finally {
            scanner.close(); // Close the scanner when we're completely done with it.
        }
    }

    public Game(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.guesses = new ArrayList<>();
        this.attempts = 0; // Initialize attempt counter
        setupGame();
    }

    private void setupGame() {
        int maxNumber = 10; // Default for EASY
        String description = "Guess a number between 1 and %d.";

        if (this.difficulty == Difficulty.NORMAL) {
            maxNumber = 50;
        } else if (this.difficulty == Difficulty.HARD) {
            maxNumber = 100;
        }

        this.answer = new Random().nextInt(maxNumber) + 1;
        System.out.printf("You have selected %s mode. ", difficulty);
        System.out.println(String.format(description, maxNumber));
        if (debuggerMode) {
            System.out.println("Debugger Mode: " + answer); // Show the answer in debugger mode
        }
        startGame();
    }

    private void startGame() {
        while (true) {
            System.out.print("\nEnter your guess: ");
            int guess = readValidInteger();
            guesses.add(guess);
            attempts++;

            if (guess == answer) {
                String congratsMessage = generateCongratsMessage(attempts);
                System.out.println(congratsMessage);
                if (attempts == 1) {
                    displayCredits();
                }
                break;
            } else {
                provideHint(guess);
                System.out.println("Attempts: " + attempts);
            }
        }
        promptForNextAction();
    }

    private String generateCongratsMessage(int attempts) {
        if (attempts == 1) {
            return "WHOA! You got it on your FIRST try. YOU WIN! ROLL CREDITS!";
        } else if (attempts <= 10) {
            return String.format("\nYES! You got it in %d attempts. Congrats! Think you can guess the number with even less guesses?", attempts);
        } else if (attempts <= 30) {
            return String.format("\nYES! You got it in %d attempts. Nice work! Want to try again for a better score?", attempts);
        } else {
            return String.format("\nYES! You got it in %d attempts. That sort of took a while... think you can beat your own record next time?", attempts);
        }
    }

    private void displayCredits() {
        System.out.println("\nCredits:");
        System.out.println("Designer: William Quiroga");
        System.out.println("Coder: William Quiroga");
        System.out.println("Creative Mind: William Quiroga");
        System.out.println("Lead Tester: William Quiroga");
        System.out.println("Support Team of One: William Quiroga");
        System.out.println("Snack Provider: William Quiroga");
        System.out.println("Motivational Speaker: William Quiroga");
        System.out.println("Literally Everything else: William Quiroga");
        System.out.println("\nYou've unlocked CUSTOM mode. In the difficulty selection, select 4 to enter Custom Mode.");
    }

    private int readValidInteger() {
        while (!scanner.hasNextInt()) {
            System.out.println("That's not a number. Please enter a valid number.");
            scanner.next(); // Discard non-integer input
        }
        int number = scanner.nextInt();
        scanner.nextLine(); // Consume the newline left-over
        return number;
    }

    private void provideHint(int guess) {
        if (difficulty == Difficulty.EASY || difficulty == Difficulty.NORMAL) {
            if (guess < answer) {
                System.out.println("Hint: Guess higher!");
            } else {
                System.out.println("Hint: Guess lower!");
            }
        } else if (difficulty == Difficulty.HARD) {
            if (guess < answer) {
                System.out.println(HIGHER_HINTS[rand.nextInt(HIGHER_HINTS.length)]);
            } else {
                System.out.println(LOWER_HINTS[rand.nextInt(LOWER_HINTS.length)]);
            }
        }
    }

    private void promptForNextAction() {
        System.out.println("\nWould you like to:");
        System.out.println("1. Play again");
        System.out.println("2. Change difficulty");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");

        int choice = readValidInteger();
        switch (choice) {
            case 1:
                new Game(difficulty).setupGame();
                break;
            case 2:
                showMainMenu();
                break;
            case 0:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
                promptForNextAction();
                break;
        }
    }

    public static void showMainMenu() {
        System.out.println("\nWelcome to Single Shot Guess, the Command-Line Game!\n");
        System.out.println("1. Easy - Guess a number between 1-10.");
        System.out.println("2. Normal - Guess a number between 1-50. With high or low hints.");
        System.out.println("3. Hard - Guess a number between 1-100. Good luck ;)");
        System.out.println("0. Exit");
        System.out.print("\nSelect a difficulty level: ");
    
        int choice = scanner.nextInt(); // Use the static class-level scanner
        scanner.nextLine(); // Consume the rest of the line to avoid input issues
        if (choice == 5) {
            debuggerMode = !debuggerMode; // Toggle the debugger mode
            System.out.println("Debugger mode is now " + (debuggerMode ? "ON." : "OFF."));
            showMainMenu();
        return;
    } else if (choice == 4) {
        System.out.println("\nCUSTOM MODE Coming Soon! Or not... who knows -_-");
        showMainMenu();
        return;
    }
    
        Difficulty selectedDifficulty = Difficulty.EASY; // Default
        switch (choice) {
            case 1:
                selectedDifficulty = Difficulty.EASY;
                break;
            case 2:
                selectedDifficulty = Difficulty.NORMAL;
                break;
            case 3:
                selectedDifficulty = Difficulty.HARD;
                break;
            case 0:
                System.exit(0);
                return;
            default:
                System.out.println("Invalid selection. Please try again.");
                showMainMenu();
                return;
        }
        new Game(selectedDifficulty);
    }
    

    enum Difficulty {
        EASY, NORMAL, HARD, CUSTOM
    }
}
