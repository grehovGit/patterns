package undo;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collections;

public class UndoDemo {
    public static void main(String[] args) {
        BankAccount bankAccount = new BankAccount();
        System.out.println(bankAccount);

        ArrayList<Command> commands = new ArrayList<Command>();
        commands.add(new BankAccountCommand(bankAccount, BankAccountCommand.Action.DPOSIT, 100));
        commands.add(new BankAccountCommand(bankAccount, BankAccountCommand.Action.WITHDRAW, 1000));

        for (Command c : commands) {
            c.call();
            System.out.println(bankAccount);
        }

        Collections.reverse(commands);

        for (Command c : Lists.reverse(commands)) {
            c.undo();
            System.out.println(bankAccount);
        }

    }
}

class BankAccount {
    private int balance;
    private int overdraftLimit = -500;

    public void deposit(int amount) {
        balance += amount;
        System.out.println("Deposit " + amount + ", balance " + balance);
    }

    public boolean withdraw(int amount) {
        if (balance - amount >= overdraftLimit) {
            balance -= amount;
            System.out.println("Withdrew " + amount + ", balance " + balance);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "balance=" + balance +
                ", overdraftLimit=" + overdraftLimit +
                '}';
    }
}

interface Command {
    void call();
    void undo();
}

class BankAccountCommand implements Command {
    private BankAccount account;
    private boolean succeeded;

    @Override
    public void call() {
        switch (action) {
            case DPOSIT:
                succeeded = true;
                account.deposit(amount);
                break;
            case WITHDRAW:
                succeeded = account.withdraw(amount);
                break;
        }
    }

    @Override
    public void undo() {
        if (!succeeded) return;

        switch (action) {
            case DPOSIT:
                account.withdraw(amount);
                break;
            case WITHDRAW:
                account.deposit(amount);
                break;
        }
    }


    public enum Action {
        DPOSIT, WITHDRAW
    }

    private Action action;
    private int amount;

    public BankAccountCommand(BankAccount account, Action action, int amount) {
        this.account = account;
        this.action = action;
        this.amount = amount;
    }
}

