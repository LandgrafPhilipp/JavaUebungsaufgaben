package bank;

import java.util.ArrayList;

public class Customer {

    private String name;
    private ArrayList<Account> account;

    public Customer(String name){
        this.name = name;
        account = new ArrayList<>();
    }

    public ArrayList<Account> getAccount() {
        return account;
    }
}
