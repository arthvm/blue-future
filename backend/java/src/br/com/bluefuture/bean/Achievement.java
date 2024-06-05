package br.com.bluefuture.bean;

/**
 * Esta classe abstrata representa uma Conquista com um nome, descrição e nível de conquista.
 * Ela fornece métodos para obter e definir o nome, descrição e nível de conquista,
 * aumentar o nível de conquista e métodos abstratos para desbloquear e atualizar a conquista.
 */
public abstract class Achievement {
    private String name;
    private String description;
    private int achievementLevel;

    /**
     * Construtor padrão.
     */
    public Achievement() {
    }

    /**
     * Construtor com parâmetros.
     * @param name O nome da conquista.
     * @param description A descrição da conquista.
     * @param achievementLevel O nível da conquista.
     */
    public Achievement(String name, String description, int achievementLevel) {
        this.setName(name);
        this.setDescription(description);
        this.achievementLevel = achievementLevel;
    }

    /**
     * @return O nome da conquista.
     */
    public String getName() {
        return name;
    }

    /**
     * Define o nome da conquista.
     * @param name O nome a ser definido. Não pode ser nulo.
     * @throws IllegalArgumentException Se o nome for nulo.
     */
    public void setName(String name) {
        if (name == null) throw new IllegalArgumentException("O nome da conquista não pode estar vazio");
        this.name = name;
    }

    /**
     * @return A descrição da conquista.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Define a descrição da conquista.
     * @param description A descrição a ser definida.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return O nível da conquista.
     */
    public int getAchievementLevel() {
        return achievementLevel;
    }

    /**
     * Aumenta o nível da conquista em 1.
     */
    public void increaseLevel(){
        this.achievementLevel++;
    }

    /**
     * Método abstrato para verificar se um usuário pode desbloquear a conquista.
     * @param user O usuário a ser verificado.
     * @return A conquista se ela puder ser desbloqueada, nulo caso contrário.
     */
    public abstract Achievement canUnlock(User user);

    /**
     * Método abstrato para verificar se um usuário pode atualizar a conquista.
     * @param user O usuário a ser verificado.
     * @return A conquista se ela puder ser atualizada, nulo caso contrário.
     */
    public abstract Achievement canUpgrade(User user);

    /**
     * @return A representação em string da conquista.
     */
    @Override
    public String toString() {
        return super.toString();
    }
}