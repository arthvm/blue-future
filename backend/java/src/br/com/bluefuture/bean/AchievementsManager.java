package br.com.bluefuture.bean;

import java.util.ArrayList;
import java.util.List;

public class AchievementsManager {
    private static List<Achievement> achievements = new ArrayList<>();

    static {
        addAchievement(new LevelAchievement());
    }

    private AchievementsManager() {
    }

    private AchievementsManager(List<Achievement> achievementsList){
        AchievementsManager.achievements = achievementsList;
    }

    public static List<Achievement> getAchievements() {
        return AchievementsManager.achievements;
    }

    public static void setAchievements(List<Achievement> achievements) {
        if(achievements == null) throw new IllegalArgumentException("Achievements can't be null");
        AchievementsManager.achievements = achievements;
    }

    public static void addAchievement(Achievement achievement){
        if(achievement == null) throw new IllegalArgumentException("Achievement can't be null");
        if(AchievementsManager.achievements.contains(achievement)) throw new IllegalArgumentException("Achievement already exists");

        achievements.add(achievement);
    }


    public static void checkActions(User user) {
        for(Achievement achievement: achievements){
            Achievement achievementToUnlock = achievement.canUnlock(user);
            if(achievementToUnlock != null) user.addAchievement(achievementToUnlock);

            Achievement achievementToUpgrade = achievement.canUpgrade(user);
            if(achievementToUpgrade != null){
                int index = user.getAchievements().indexOf(achievementToUpgrade);
                user.getAchievements().get(index).increaseLevel();
            }
        }
    }
}
