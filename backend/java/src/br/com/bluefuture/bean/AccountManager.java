package br.com.bluefuture.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountManager {
    private static Map<String, Account> accounts = new HashMap<>();

    private AccountManager(){

    }

    private AccountManager(Map<String, Account> accountList) {
        AccountManager.accounts = accountList;
    }

    public static Map<String, Account> getAccounts() {
        return AccountManager.accounts;
    }

    public static List<Organization> getOrganizations(){
        List<Organization> organizations = new ArrayList<>();

        for (Account account: AccountManager.accounts.values()){
            if(account instanceof Organization) organizations.add((Organization) account);
        }

        return organizations;
    }

    public static void register(Account account){
        if(account == null) throw new IllegalArgumentException("Account can't be null");
        if(AccountManager.accounts.containsKey(account.getEmail())) throw new IllegalArgumentException("Account already exists");

        AccountManager.accounts.put(account.getEmail(), account);
    }

    public static Account login(String email, String password){
        Account account = AccountManager.accounts.get(email);

        if(account == null) throw new IllegalArgumentException("Account does not exist");
        if(!account.getPassword().equals(password)) throw new IllegalArgumentException("Password is incorrect");

        return account;
    }
}
