package br.com.bluefuture.bean;

import java.util.HashMap;
import java.util.Map;

public class AccountManager {
    private final Map<String, Account> accounts;

    public AccountManager(){
        this.accounts = new HashMap<>();
    }

    public AccountManager(Map<String, Account> accounts) {
        this.accounts = accounts;
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }

    public void register(Account account){
        if(account == null) throw new IllegalArgumentException("Account can't be null");
        if(accounts.containsKey(account.getEmail())) throw new IllegalArgumentException("Account already exists");

        accounts.put(account.getEmail(), account);
    }

    public Account login(String email, String password){
        Account account = accounts.get(email);

        if(account == null) throw new IllegalArgumentException("Account does not exist");
        if(!account.getPassword().equals(password)) throw new IllegalArgumentException("Password is incorrect");

        return account;
    }
}
