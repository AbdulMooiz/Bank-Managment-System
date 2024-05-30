package bannk;

import java.util.ArrayList;
import java.util.Scanner;

abstract class Account {
    private final String accountNumber;
    private double balance;

    public Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited: " + amount);
    }

    protected void setBalance(double balance) {
        this.balance = balance;
    }

    public abstract void withdraw(double amount);
}

class SavingsAccount extends Account {
    public SavingsAccount(String accountNumber, double balance) {
        super(accountNumber, balance);
    }

    @Override
    public void withdraw(double amount) {
        if (getBalance() >= amount) {
            setBalance(getBalance() - amount);
            System.out.println("Withdrew: " + amount);
        } else {
            System.out.println("Insufficient balance.");
        }
    }
}

class CurrentAccount extends Account {
    private final double overdraftLimit;

    public CurrentAccount(String accountNumber, double balance, double overdraftLimit) {
        super(accountNumber, balance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) {
        if (getBalance() + overdraftLimit >= amount) {
            setBalance(getBalance() - amount);
            System.out.println("Withdrew: " + amount);
        } else {
            System.out.println("Overdraft limit exceeded.");
        }
    }
}

public class BankManagementSystem {
    private static final ArrayList<Account> accounts = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try (scanner) {
            boolean exit = false;
            while (!exit)
             {
                System.out.println("1. Create Savings Account");
                System.out.println("2. Create Current Account");
                System.out.println("3. Deposit");
                System.out.println("4. Withdraw");
                System.out.println("5. View Balance");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();
                
                switch (choice) {
                    case 1 -> createSavingsAccount();
                    case 2 -> createCurrentAccount();
                    case 3 -> performDeposit();
                    case 4 -> performWithdrawal();
                    case 5 -> viewBalance();
                    case 6 -> {
                       exit = true;
                        System.out.println("Exiting...");
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }

    private static void createSavingsAccount() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter initial balance: ");
        double balance = scanner.nextDouble();
        accounts.add(new SavingsAccount(accountNumber, balance));
        System.out.println("Savings Account created successfully.");
    }

    private static void createCurrentAccount() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter initial balance: ");
        double balance = scanner.nextDouble();
        System.out.print("Enter overdraft limit: ");
        double overdraftLimit = scanner.nextDouble();
        accounts.add(new CurrentAccount(accountNumber, balance, overdraftLimit));
        System.out.println("Current Account created successfully.");
    }

    private static void performDeposit() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();
        Account account = findAccount(accountNumber);
        if (account != null) {
            account.deposit(amount);
            System.out.println("Deposit Transaction Completed.");
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void performWithdrawal() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();
        Account account = findAccount(accountNumber);
        if (account != null) {
            account.withdraw(amount);
            System.out.println("Withdrawal Transaction Completed.");
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void viewBalance() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        Account account = findAccount(accountNumber);
        if (account != null) {
            System.out.println("Account Balance: " + account.getBalance());
        } else {
            System.out.println("Account not found.");
        }
    }

    private static Account findAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }
}
