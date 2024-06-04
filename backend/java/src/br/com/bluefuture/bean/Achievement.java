package br.com.bluefuture.bean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Achievement {
    private String name;
    private String description;

    public Achievement() {
    }

    public Achievement(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) throw new IllegalArgumentException("Name of achievement can't be empty");

        String regex = "^[A-Za-zÀ-ÖØ-öø-ÿ]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);

        if (!matcher.matches()) throw new IllegalArgumentException("Name of the achievement must contain at least 2 " +
                "letters and can include accents.");

        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public abstract void unlock();
}
