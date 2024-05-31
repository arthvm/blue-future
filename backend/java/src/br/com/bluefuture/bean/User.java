package br.com.bluefuture.bean;

import java.util.ArrayList;
import java.util.List;

public class User extends Account{
    private int level;
    private int xp;
    private static final int XP_PER_LEVEL = 100;
    private final List<Achievement> achievements = new ArrayList<>();
    private final List<Event> events = new ArrayList<>();
    private Organization association;

    public User() {}

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
