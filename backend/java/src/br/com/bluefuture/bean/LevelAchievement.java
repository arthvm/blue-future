package br.com.bluefuture.bean;

/**
 * Esta classe representa uma Conquista de Nível no sistema.
 * Ela estende a classe Achievement e fornece métodos para desbloquear e atualizar a conquista.
 */
public class LevelAchievement extends Achievement{

    /**
     * Construtor padrão.
     * Define o nome e a descrição da conquista.
     */
    public LevelAchievement() {
        setName("Fazendo a diferenca!");
        setDescription("Reporte, colete e participe de " +
                "eventos para ganhar XP e subir de nivel!");
    }

    /**
     * Construtor com parâmetros.
     * @param name O nome da conquista.
     * @param description A descrição da conquista.
     * @param achievementLevel O nível da conquista.
     */
    public LevelAchievement(String name, String description, int achievementLevel) {
        super(name, description, achievementLevel);
    }

    /**
     * Verifica se o usuário pode desbloquear a conquista.
     * @param user O usuário a ser verificado.
     * @return A conquista se o usuário puder desbloqueá-la, null caso contrário.
     * @throws IllegalArgumentException Se o usuário for nulo.
     */
    @Override
    public Achievement canUnlock(User user) {
        if(user == null) throw new IllegalArgumentException("Usuário não pode ser nulo");

        if(user.getLevel() >= 5) return new LevelAchievement();

        return null;
    }

    /**
     * Verifica se o usuário pode atualizar a conquista.
     * @param user O usuário a ser verificado.
     * @return A conquista se o usuário puder atualizá-la, null caso contrário.
     * @throws IllegalArgumentException Se o usuário for nulo.
     */
    @Override
    public Achievement canUpgrade(User user) {
        if(user == null) throw new IllegalArgumentException("Usuário não pode ser nulo");

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