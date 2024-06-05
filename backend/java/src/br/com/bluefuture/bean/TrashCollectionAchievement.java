package br.com.bluefuture.bean;

/**
 * Esta classe representa uma Conquista de Coleta de Lixo no sistema.
 * Ela estende a classe Achievement e fornece métodos para desbloquear e atualizar a conquista.
 */
public class TrashCollectionAchievement extends Achievement{

    /**
     * Construtor padrão.
     * Define o nome e a descrição da conquista.
     */
    public TrashCollectionAchievement() {
        setName("Maos na agua!");
        setDescription("Por que apenas reportar lixo se você pode coletá-lo?");
    }

    /**
     * Construtor com parâmetros.
     * @param name O nome da conquista.
     * @param description A descrição da conquista.
     * @param achievementLevel O nível da conquista.
     */
    public TrashCollectionAchievement(String name, String description, int achievementLevel) {
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

        if(user.getTrashCollected() >= 5) return new TrashCollectionAchievement();

        return null;
    }

    /**
     * Verifica se o usuário pode atualizar a conquista.
     * @param user O usuário a ser verificado.
     * @return A conquista se ela puder ser atualizada, nulo caso contrário.
     */
    @Override
    public Achievement canUpgrade(User user) {
        if(user == null) throw new IllegalArgumentException("Usuário não pode ser nulo");

        TrashCollectionAchievement trashCollectionAchievement = null;
        for(Achievement achievement : user.getAchievements()){
            if (achievement.getName().equals(this.getName())){
                trashCollectionAchievement = (TrashCollectionAchievement) achievement;
            }
        }

        if(trashCollectionAchievement == null || !(user.getTrashCollected() >=
                (trashCollectionAchievement.getAchievementLevel() + 1) * 5)) return null;

        return trashCollectionAchievement;
    }
}