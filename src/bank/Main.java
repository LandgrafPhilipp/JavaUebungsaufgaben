package bank;

import java.util.concurrent.locks.ReentrantLock;

public class Main{

    public static void main(String[] args) {
        Bank sparkasse = new Bank(79090500);
        Bank sparda = new Bank(79050000);

        Account accountSparkasseMatthes = new Account( 111333444, 7491.23 , sparkasse);
        Account accountSparkasseWeber = new Account( 111333555, 5422.01 , sparkasse);
        Account accountSpardaMW = new Account(222333444, 73, sparda);

        Customer matthes = new Customer("Matthes");
        Customer weber = new Customer("Weber");

        matthes.getAccount().add(accountSparkasseMatthes);
        matthes.getAccount().add(accountSpardaMW);

        weber.getAccount().add(accountSparkasseWeber);
        weber.getAccount().add(accountSpardaMW);

        int amount = 50;

        for(int i = 0; i < 10; i++){
            int finalAmount = amount;
            new Thread(new Runnable(){
                @Override
                public void run(){
                    ReentrantLock lock = new ReentrantLock();
                    lock.lock();
                    accountSparkasseMatthes.transfer(accountSpardaMW, finalAmount);
                    if(accountSpardaMW.getCount() >= 20){
                        accountSpardaMW.transfer(accountSparkasseMatthes, accountSpardaMW.getBalance() + 100);
                    }
                    lock.unlock();
                }
            }).start();
            new Thread(new Runnable(){
                @Override
                public void run(){
                    accountSparkasseWeber.transfer(accountSpardaMW, finalAmount);
                    if(accountSpardaMW.getCount() >= 20){
                        accountSpardaMW.transfer(accountSparkasseWeber, accountSpardaMW.getBalance() + 100);
                    }
                }
            }).start();
            amount +=5;
        }


    }
}
