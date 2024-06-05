package br.com.bluefuture.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Esta classe gerencia as contas no sistema.
 * Ela fornece métodos para obter contas, registrar uma nova conta e fazer login em uma conta.
 */
public class AccountManager {
    /**
     * Um mapa para armazenar as contas. A chave é o email da conta e o valor é o objeto da conta.
     */
    private static Map<String, Account> accounts = new HashMap<>();

    /**
     * Construtor privado para prevenir a instanciação.
     */
    private AccountManager(){

    }

    /**
     * Construtor privado para inicializar o mapa de contas.
     * @param accountList A lista inicial de contas.
     */
    private AccountManager(Map<String, Account> accountList) {
        AccountManager.accounts = accountList;
    }

    /**
     * @return O mapa de contas.
     */
    public static Map<String, Account> getAccounts() {
        return AccountManager.accounts;
    }

    /**
     * @return Uma lista de organizações nas contas.
     */
    public static List<Organization> getOrganizations(){
        List<Organization> organizations = new ArrayList<>();

        for (Account account: AccountManager.accounts.values()){
            if(account instanceof Organization) organizations.add((Organization) account);
        }

        return organizations;
    }

    /**
     * Registra uma nova conta.
     * @param account A conta a ser registrada.
     * @throws IllegalArgumentException Se a conta for nula ou já existir.
     */
    public static void register(Account account){
        if(account == null) throw new IllegalArgumentException("A conta não pode ser nula");
        if(AccountManager.accounts.containsKey(account.getEmail())) throw new IllegalArgumentException("A conta já existe");

        AccountManager.accounts.put(account.getEmail(), account);
    }

    /**
     * Faz login em uma conta.
     * @param email O email da conta.
     * @param password A senha da conta.
     * @return A conta logada.
     * @throws IllegalArgumentException Se a conta não existir ou a senha estiver incorreta.
     */
    public static Account login(String email, String password){
        Account account = AccountManager.accounts.get(email);

        if(account == null) throw new IllegalArgumentException("A conta não existe");
        if(!account.getPassword().equals(password)) throw new IllegalArgumentException("A senha está incorreta");

        return account;
    }
}