package br.com.bluefuture.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta classe gerencia as conquistas no sistema.
 * Ela fornece métodos para obter conquistas, adicionar uma nova conquista e verificar as ações de um usuário.
 */
public class AchievementsManager {
    /**
     * Uma lista para armazenar as conquistas.
     */
    private static List<Achievement> achievements = new ArrayList<>();

    // Utiliza o bloco estático para adicionar as conquistas iniciais.
    static {
        addAchievement(new LevelAchievement());
    }

    /**
     * Construtor privado para prevenir a instanciação.
     */
    private AchievementsManager() {
    }

    /**
     * Construtor privado para inicializar a lista de conquistas.
     * @param achievementsList A lista inicial de conquistas.
     */
    private AchievementsManager(List<Achievement> achievementsList){
        AchievementsManager.achievements = achievementsList;
    }

    /**
     * @return A lista de conquistas.
     */
    public static List<Achievement> getAchievements() {
        return AchievementsManager.achievements;
    }

    /**
     * Define a lista de conquistas.
     * @param achievements A lista de conquistas a ser definida.
     * @throws IllegalArgumentException Se a lista de conquistas for nula.
     */
    public static void setAchievements(List<Achievement> achievements) {
        if(achievements == null) throw new IllegalArgumentException("Conquistas não podem ser nulas");
        AchievementsManager.achievements = achievements;
    }

    /**
     * Adiciona uma nova conquista à lista.
     * @param achievement A conquista a ser adicionada.
     * @throws IllegalArgumentException Se a conquista for nula ou já existir.
     */
    public static void addAchievement(Achievement achievement){
        if(achievement == null) throw new IllegalArgumentException("Conquista não pode ser nula");
        if(AchievementsManager.achievements.contains(achievement)) throw new IllegalArgumentException("Conquista já existe");

        achievements.add(achievement);
    }

    /**
     * Verifica as ações de um usuário para desbloquear ou atualizar conquistas.
     * @param user O usuário a ser verificado.
     */
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