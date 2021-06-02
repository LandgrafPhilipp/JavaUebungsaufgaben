package bank;

import java.util.concurrent.locks.ReentrantLock;

public class Account{

    private int accountnumber;
    private double balance;
    private Bank bank;
    private int count;

    public Account(int accountnumber, double balance, Bank bank) {
        this.accountnumber = accountnumber;
        this.balance = balance;
        this.bank = bank;
        count = 0;
    }

    public void withdraw(double amount){
        balance -= amount;
    }

    public void pay(double amount){
        balance += amount;
    }

    public void transfer(Account account, double amount){
        account.setBalance(account.getBalance() + amount);
        balance -= amount;
        account.setCount(account.getCount() + 1);
    }

    public int getCount(){
        return count;
    }

    public void setCount(int count){
        this.count = count;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
