package br.com.bluefuture.bean;

import java.util.ArrayList;
import java.util.List;

public class User extends Account{
    private int trashReported;
    private int trashCollected;
    private int level;
    private int xp;
    private static final int XP_PER_LEVEL = 100;
    private final List<Achievement> achievements = new ArrayList<>();
    private final List<Event> events = new ArrayList<>();
    private Organization association;

    public User() {}

    public User(String name, String email, String password, int trashReported, int trashCollected, int level, int xp, Organization association) {
        super(name, email, password);
        this.trashReported = trashReported;
        this.trashCollected = trashCollected;
        this.level = level;
        this.xp = xp;
        this.association = association;
    }

    public int getTrashReported() {
        return trashReported;
    }

    public int getTrashCollected() {
        return trashCollected;
    }

    public int getLevel() {
        return level;
    }

    public int getXp() {
        return xp;
    }

    public List<Achievement> getAchievements() {
        return achievements;
    }

    public List<Event> getEvents() {
        return events;
    }

    public Organization getAssociation() {
        return association;
    }

    public void setAssociation(Organization association) {
        this.association = association;
    }

    public void reportTrash(String location, int severity, boolean wasCollected){
        if (location == null) throw new IllegalArgumentException("Location can't be empty");
        if (severity < 1 || severity > 3) throw new IllegalArgumentException("Severity can't be less than " +
                "0 or more than 2");

        if (wasCollected){
            increaseXP(5 * (severity + 1));
            trashCollected++;
        }

        increaseXP(10);
        trashReported++;
    }

    public void addAchievement(Achievement achievement){
        if(achievement == null) throw new IllegalArgumentException("Achievement can't be empty");

        this.achievements.add(achievement);
    }

    public void joinEvent(Event event){
        if(event == null) throw new IllegalArgumentException("Event can't be empty");

        this.events.add(event);
    }

    public void increaseXP(int amount){
        if (amount <= 0 ) throw new IllegalArgumentException("XP Amount can't be less or equal to 0");

        this.xp += amount;
        this.level = (int)Math.floor((double) (this.xp + XP_PER_LEVEL) / XP_PER_LEVEL);
    }
}
