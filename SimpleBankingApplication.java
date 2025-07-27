import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

class BankAccount {
    private final String accountNumber;
    private final String pin;
    private double balance;
    private final ArrayList<String> transactionHistory;

    public BankAccount(String pin) {
        this.accountNumber = generateAccountNumber();
        this.pin = pin;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    private String generateAccountNumber() {
        Random random = new Random();
        return String.format("%04d", random.nextInt(10000));
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add("Deposited: Rs." + amount);
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactionHistory.add("Withdrew: Rs." + amount);
            return true;
        } else {
            System.out.println("Insufficient balance!");
            return false;
        }
    }

    public void printTransactionHistory() {
        System.out.println("Transaction History:");
        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }
}

public class SimpleBankingApplication {
    private static final HashMap<String, BankAccount> accounts = new HashMap<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to the Simple Banking Application!");

        while (true) {
            System.out.println("1. Create Account");
            System.out.println("2. Access Account");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> createAccount();
                case 2 -> accessAccount();
                case 3 -> {
                    System.out.println("Thank you for using the Simple Banking Application!");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void createAccount() {
        System.out.print("Enter a 4-digit PIN: ");
        String pin = scanner.nextLine();
        BankAccount account = new BankAccount(pin);
        accounts.put(account.getAccountNumber(), account);
        System.out.println("Account created successfully! Your account number is: " + account.getAccountNumber());
    }

    private static void accessAccount() {
        System.out.print("Enter your account number: ");
        String accountNumber = scanner.nextLine();
        BankAccount account = accounts.get(accountNumber);

        if (account == null) {
            System.out.println("Account not found!");
            return;
        }

        System.out.print("Enter your PIN: ");
        String pin = scanner.nextLine();

        if (!account.getPin().equals(pin)) {
            System.out.println("Incorrect PIN!");
            return;
        }

        boolean sessionActive = true;
        while (sessionActive) {
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. Transaction History");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter amount to deposit: Rs");
                    double depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount);
                    System.out.println("Deposited: Rs" + depositAmount);
                }
                case 2 -> {
                    System.out.print("Enter amount to withdraw: Rs");
                    double withdrawAmount = scanner.nextDouble();
                    account.withdraw(withdrawAmount);
                }
                case 3 -> System.out.println("Current Balance: Rs" + account.getBalance());
                case 4 -> account.printTransactionHistory();
                case 5 -> sessionActive = false;
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
