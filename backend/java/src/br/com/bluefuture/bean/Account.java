package br.com.bluefuture.bean;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Account {
    private String userName;
    private String name;
    private String email;
    private String password;

    public Account() {}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        if(userName == null) throw new IllegalArgumentException("Username can't be empty");

        String userNameRegex = "^[a-zA-Z0-9_.-]{3,}$";
        Pattern pattern = Pattern.compile(userNameRegex);
        Matcher matcher = pattern.matcher(userName);

        if(!matcher.matches()) throw new IllegalArgumentException("Username must be at least 3 characters long and may " +
                "contain letters, digits, underscores, hyphens, and periods.");

        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) throw new IllegalArgumentException("Name can't be empty");

        String regex = "^[A-Za-zÀ-ÖØ-öø-ÿ]{3,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);

        if (!matcher.matches()) throw new IllegalArgumentException("Name must contain at least 3 letters and can include accents.");

        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (name == null) throw new IllegalArgumentException("Email can't be empty");

        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if(!matcher.matches()) throw new IllegalArgumentException("Invalid email address format.");

        this.email = email;
    }

    public void setPassword(String password) {
        if (password == null) throw new IllegalArgumentException("Password can't be empty");

        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        if(!matcher.matches()) throw new IllegalArgumentException("Invalid password format. Password must be at least 8 " +
                "characters long and include at least one uppercase letter, one lowercase letter, one digit, and one " +
                "special character.");

        this.password = password;
    }

}
