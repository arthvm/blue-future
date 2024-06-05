package br.com.bluefuture.bean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Esta classe representa uma Conta com nome, email e senha.
 */
public class Account {
    private String name;
    private String email;
    private String password;

    /**
     * Construtor padrão.
     */
    public Account() {}

    /**
     * Construtor com parâmetros.
     * @param name O nome do titular da conta.
     * @param email O email do titular da conta.
     * @param password A senha da conta.
     */
    public Account(String name, String email, String password) {
        this.setName(name);
        this.setEmail(email);
        this.setPassword(password);
    }

    /**
     * @return O nome do titular da conta.
     */
    public String getName() {
        return name;
    }

    /**
     * Define o nome do titular da conta.
     * @param name O nome a ser definido. Deve ter pelo menos 3 caracteres e pode incluir acentos.
     * @throws IllegalArgumentException Se o nome for nulo ou não corresponder ao formato exigido.
     */
    public void setName(String name) {
        if (name == null) throw new IllegalArgumentException("O nome não pode estar vazio");

        String regex = "^[A-Za-zÀ-ÖØ-öø-ÿ-\\s]{3,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);

        if (!matcher.matches()) throw new IllegalArgumentException("O nome deve conter pelo menos 3 letras e pode incluir acentos.");

        this.name = name;
    }

    /**
     * @return O email do titular da conta.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define o email do titular da conta.
     * @param email O email a ser definido. Deve estar em um formato de email válido.
     * @throws IllegalArgumentException Se o email for nulo ou não corresponder ao formato exigido.
     */
    public void setEmail(String email) {
        if (name == null) throw new IllegalArgumentException("O email não pode estar vazio");

        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if(!matcher.matches()) throw new IllegalArgumentException("Formato de endereço de email inválido.");

        this.email = email;
    }

    /**
     * Define a senha da conta.
     * @param password A senha a ser definida. Deve ter pelo menos 8 caracteres e incluir pelo menos uma letra maiúscula, uma letra minúscula, um dígito e um caractere especial.
     * @throws IllegalArgumentException Se a senha for nula ou não corresponder ao formato exigido.
     */
    public void setPassword(String password) {
        if (password == null) throw new IllegalArgumentException("A senha não pode estar vazia");

        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        if(!matcher.matches()) throw new IllegalArgumentException("Formato de senha inválido. A senha deve ter pelo menos 8 " +
                "caracteres e incluir pelo menos uma letra maiúscula, uma letra minúscula, um dígito e um caractere especial.");

        this.password = password;
    }

    /**
     * @return A senha da conta.
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return O nome do titular da conta.
     */
    @Override
    public String toString() {
        return this.getName();
    }
}