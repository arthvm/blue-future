package br.com.bluefuture.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta classe representa um Usuário no sistema.
 * Ela estende a classe Account e fornece métodos para gerenciar lixo reportado, lixo coletado, nível, xp, conquistas, eventos e associação.
 */
public class User extends Account{
    /**
     * O número de lixo reportado pelo usuário.
     */
    private int trashReported;
    /**
     * O número de lixo coletado pelo usuário.
     */
    private int trashCollected;
    /**
     * O nível do usuário.
     */
    private int level = 1;
    /**
     * O xp do usuário.
     */
    private int xp;
    /**
     * O xp necessário por nível.
     */
    private static final int XP_PER_LEVEL = 100;
    /**
     * Uma lista de conquistas do usuário.
     */
    private final List<Achievement> achievements = new ArrayList<>();
    /**
     * Uma lista de eventos que o usuário está participando.
     */
    private final List<Event> events = new ArrayList<>();
    /**
     * A organização à qual o usuário está associado.
     */
    private Organization association;

    /**
     * Construtor padrão.
     */
    public User() {}

    /**
     * Construtor com parâmetros.
     * @param name O nome do usuário.
     * @param email O email do usuário.
     * @param password A senha do usuário.
     * @param trashReported O número de lixo reportado pelo usuário.
     * @param trashCollected O número de lixo coletado pelo usuário.
     * @param level O nível do usuário.
     * @param xp O xp do usuário.
     * @param association A organização à qual o usuário está associado.
     */
    public User(String name, String email, String password, int trashReported, int trashCollected, int level, int xp, Organization association) {
        super(name, email, password);
        this.trashReported = trashReported;
        this.trashCollected = trashCollected;
        this.level = level;
        this.xp = xp;
        this.association = association;
    }

    /**
     * @return O número de lixo reportado pelo usuário.
     */
    public int getTrashReported() {
        return trashReported;
    }

    /**
     * @return O número de lixo coletado pelo usuário.
     */
    public int getTrashCollected() {
        return trashCollected;
    }

    /**
     * @return O nível do usuário.
     */
    public int getLevel() {
        return level;
    }

    /**
     * @return O xp do usuário.
     */
    public int getXp() {
        return xp;
    }

    /**
     * @return A lista de conquistas do usuário.
     */
    public List<Achievement> getAchievements() {
        return achievements;
    }

    /**
     * @return A lista de eventos que o usuário está participando.
     */
    public List<Event> getEvents() {
        return events;
    }

    /**
     * @return A organização à qual o usuário está associado.
     */
    public Organization getAssociation() {
        return association;
    }

    /**
     * Define a organização à qual o usuário está associado.
     * @param association A organização a ser definida.
     */
    public void setAssociation(Organization association) {
        this.association = association;
    }

    /**
     * Reporta lixo e aumenta o xp.
     * @param location A localização do lixo.
     * @param severity A severidade do lixo.
     * @param wasCollected Se o lixo foi coletado ou não.
     * @throws IllegalArgumentException Se a localização for nula ou a severidade não estiver entre 1 e 3.
     */
    public void reportTrash(String location, int severity, boolean wasCollected){
        if (location == null) throw new IllegalArgumentException("Localização não pode ser vazia");
        if (severity < 1 || severity > 3) throw new IllegalArgumentException("Severidade não pode ser menor que " +
                "0 ou maior que 2");

        if (wasCollected){
            increaseXP(5 * (severity + 1));
            trashCollected++;
        }

        increaseXP(10);
        trashReported++;

        if(this.association != null && !wasCollected){
            Alert alert = new Alert(location, severity == 1 ? "Baixa" : severity == 2 ? "Media" : "Alta");
            association.addAlert(alert);
        }
    }

    /**
     * Adiciona uma conquista à lista de conquistas do usuário.
     * @param achievement A conquista a ser adicionada.
     * @throws IllegalArgumentException Se a conquista for nula.
     */
    public void addAchievement(Achievement achievement){
        if(achievement == null) throw new IllegalArgumentException("Conquista não pode ser vazia");
        for(Achievement userAchievement: achievements){
            if(userAchievement.getName().equals(achievement.getName())) return;
        }

        this.achievements.add(achievement);
    }

    /**
     * Participa de um evento e o adiciona à lista de eventos que o usuário está participando.
     * @param event O evento a ser participado.
     * @throws IllegalArgumentException Se o evento for nulo.
     */
    public void joinEvent(Event event){
        if(event == null) throw new IllegalArgumentException("Evento não pode ser vazio");

        event.addParticipant(this);
        this.events.add(event);
    }

    /**
     * Aumenta o xp do usuário.
     * @param amount A quantidade para aumentar o xp.
     * @throws IllegalArgumentException Se a quantidade for menor ou igual a 0.
     */
    public void increaseXP(int amount){
        if (amount <= 0 ) throw new IllegalArgumentException("Quantidade de XP não pode ser menor ou igual a 0");

        this.xp += amount;
        this.level = (int)Math.floor((double) (this.xp + XP_PER_LEVEL) / XP_PER_LEVEL);
    }
}