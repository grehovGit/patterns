import java.util.ArrayList;

public class CommandDemo {
    public static void main(String[] args) {
        BankAccount bankAccount = new BankAccount();
        System.out.println(bankAccount);

        ArrayList<BankAccountCommand> bcs = new ArrayList<BankAccountCommand>();
        bcs.add(new BankAccountCommand(bankAccount, BankAccountCommand.Action.DPOSIT, 100));
        bcs.add(new BankAccountCommand(bankAccount, BankAccountCommand.Action.WITHDRAW, 1000));

        for (BankAccountCommand c : bcs) {
            c.call();
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

    public void withdraw(int amount) {
        if (balance - amount >= overdraftLimit) {
            balance -= amount;
            System.out.println("Withdrew " + amount + ", balance " + balance);
        }
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
}

class BankAccountCommand implements  Command{
    private BankAccount account;

    @Override
    public void call() {
       switch (action) {
           case DPOSIT:
               account.deposit(amount);
               break;
           case WITHDRAW:
               account.withdraw(amount);
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
