package br.com.bluefuture.bean;

/**
 * Esta classe representa uma Conquista de Reportar o Primeiro Lixo no sistema.
 * Ela estende a classe Achievement e fornece métodos para desbloquear a conquista.
 */
public class FirstTrashAchievement extends Achievement{

    /**
     * Construtor padrão.
     * Define o nome e a descrição da conquista.
     */
    public FirstTrashAchievement() {
        setName("Primeiro passo!");
        setDescription("Reportar o seu primeiro lixo é um passo para um mundo melhor!");
    }

    /**
     * Construtor com parâmetros.
     * @param name O nome da conquista.
     * @param description A descrição da conquista.
     * @param achievementLevel O nível da conquista.
     */
    public FirstTrashAchievement(String name, String description, int achievementLevel) {
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

        if(user.getTrashReported() >= 1) return new FirstTrashAchievement();

        return null;
    }

    @Override
    public Achievement canUpgrade(User user) {
        return null;
    }
}