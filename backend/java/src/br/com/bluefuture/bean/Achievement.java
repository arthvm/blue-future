package br.com.bluefuture.bean;


public abstract class Achievement {
    private String name;
    private String description;
    private int achievementLevel;

    public Achievement() {
    }

    public Achievement(String name, String description, int achievementLevel) {
        this.setName(name);
        this.setDescription(description);
        this.achievementLevel = achievementLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) throw new IllegalArgumentException("Name of achievement can't be empty");
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAchievementLevel() {
        return achievementLevel;
    }

    public void increaseLevel(){
        this.achievementLevel++;
    }

    public abstract Achievement canUnlock(User user);
    public abstract Achievement canUpgrade(User user);

    @Override
    public String toString() {
        return super.toString();
    }
}
