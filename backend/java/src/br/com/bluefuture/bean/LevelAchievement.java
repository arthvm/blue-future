package br.com.bluefuture.bean;


public class LevelAchievement extends Achievement{

    public LevelAchievement() {
        setName("Fazendo a diferenca!");
        setDescription("Reporte, colete e participe de " +
                "eventos para ganhar XP e subir de nivel!");
    }

    public LevelAchievement(String name, String description, int achievementLevel) {
        super(name, description, achievementLevel);
    }

    @Override
    public Achievement canUnlock(User user) {
        if(user == null) throw new IllegalArgumentException("User can't be null");

        if(user.getLevel() >= 5) return new LevelAchievement();

        return null;
    }

    @Override
    public Achievement canUpgrade(User user) {
        if(user == null) throw new IllegalArgumentException("User can't be null");

        LevelAchievement levelAchievement = null;
        for(Achievement achievement : user.getAchievements()){
            if (achievement.getName().equals(this.getName())){
                levelAchievement = (LevelAchievement) achievement;
            }
        }

        if(levelAchievement == null || !(user.getLevel() >= (levelAchievement.getAchievementLevel() + 1) * 5)) return null;

        return levelAchievement;
    }
}
