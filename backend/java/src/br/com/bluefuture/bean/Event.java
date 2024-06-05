package br.com.bluefuture.bean;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Esta classe representa um Evento com um nome, descrição, data e hora, localização, organizador e participantes.
 * Ela fornece métodos para obter e definir o nome, descrição, data e hora, localização, organizador do evento,
 * adicionar um participante ao evento, obter a contagem de participantes e uma representação em string do evento.
 */
public class Event {
    private String name;
    private String description;
    private LocalDateTime dateTime;
    private String location;
    private Organization organizer;
    private final List<User> participants = new ArrayList<>();

    /**
     * Construtor padrão.
     */
    public Event() {
    }

    /**
     * Construtor com parâmetros.
     * @param name O nome do evento.
     * @param description A descrição do evento.
     * @param dateTime A data e hora do evento.
     * @param location A localização do evento.
     * @param organizer O organizador do evento.
     */
    public Event(String name, String description, LocalDateTime dateTime, String location, Organization organizer) {
        this.setName(name);
        this.setDescription(description);
        this.setDateTime(dateTime);
        this.setLocation(location);
        this.setOrganizer(organizer);
    }

    /**
     * @return O nome do evento.
     */
    public String getName() {
        return name;
    }

    /**
     * Define o nome do evento.
     * @param name O nome a ser definido. Não pode ser nulo.
     * @throws IllegalArgumentException Se o nome for nulo.
     */
    public void setName(String name) {
        if (name == null) throw new IllegalArgumentException("O nome do evento não pode ser vazio");

        this.name = name;
    }

    /**
     * @return A descrição do evento.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Define a descrição do evento.
     * @param description A descrição a ser definida.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return A data e hora do evento.
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Define a data e hora do evento.
     * @param dateTime A data e hora a ser definida. Não pode ser no passado.
     * @throws IllegalArgumentException Se a data e hora for no passado.
     */
    public void setDateTime(LocalDateTime dateTime) {
        ZoneId zoneId = ZoneId.of("America/Sao_Paulo");
        LocalDateTime currDateTime = LocalDateTime.now(zoneId);

        if(dateTime.isBefore(currDateTime)) throw new IllegalArgumentException("Não é possível criar um evento no passado");

        this.dateTime = dateTime;
    }

    /**
     * @return A localização do evento.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Define a localização do evento.
     * @param location A localização a ser definida. Deve conter um CEP válido.
     * @throws IllegalArgumentException Se a localização for nula ou não contiver um CEP válido.
     */
    public void setLocation(String location) {
        try {
            if (location == null) throw new IllegalArgumentException("Argumento inválido --> Esperado uma String não nula");

            String cep;
            String regex = "\\d{5}-?\\d{3}";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(location);

            if(!(matcher.find())) throw new IllegalArgumentException("Argumento inválido --> CEP não foi" +
                    " encontrado na String. Certifique-se de que ele tenha 8 dígitos (traço é opcional)");

            cep = matcher.group(0);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(String.format("https://viacep.com.br/ws/%s/json", cep))).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) throw new IOException("Falha ao conectar com a API --> " +
                    response.statusCode());

            if(response.body().contains("\"erro\": true")) throw new IllegalArgumentException("Argumento inválido --> " +
                    "O CEP fornecido não era válido");

            this.location = location;

        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return O organizador do evento.
     */
    public Organization getOrganizer() {
        return organizer;
    }

    /**
     * Define o organizador do evento.
     * @param organizer O organizador a ser definido. Não pode ser nulo.
     * @throws IllegalArgumentException Se o organizador for nulo.
     */
    public void setOrganizer(Organization organizer) {
        if (organizer == null) throw new IllegalArgumentException("Organizador não pode ser nulo");

        this.organizer = organizer;
    }

    /**
     * @return A lista de participantes do evento.
     */
    public List<User> getParticipants() {
        return participants;
    }

    /**
     * Adiciona um participante ao evento.
     * @param user O usuário a ser adicionado como participante. Não pode ser nulo.
     * @throws IllegalArgumentException Se o usuário for nulo.
     */
    public void addParticipant(User user){
        if (user == null) throw new IllegalArgumentException("Usuário não pode ser nulo");

        participants.add(user);
    }

    /**
     * @return A contagem de participantes do evento.
     */
    public int getParticipantCount(){
        return participants.size();
    }

    /**
     * @return A representação em string do evento.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy -- HH:mm");
        return this.getName() + "(" + getDateTime().format(formatter) + " em " + getLocation() + ")";
    }
}