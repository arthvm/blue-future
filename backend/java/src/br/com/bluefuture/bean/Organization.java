package br.com.bluefuture.bean;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Esta classe representa uma Organização no sistema.
 * Ela estende a classe Account e fornece métodos para gerenciar alertas e eventos.
 */
public class Organization extends Account{
    /**
     * A localização da organização.
     */
    private String location;
    /**
     * Uma lista de alertas associados à organização.
     */
    private final List<Alert> alerts = new ArrayList<>();
    /**
     * Uma lista de eventos associados à organização.
     */
    private final List<Event> events = new ArrayList<>();

    /**
     * Construtor padrão.
     */
    public Organization() {
    }

    /**
     * Construtor com parâmetros.
     * @param name O nome da organização.
     * @param email O email da organização.
     * @param password A senha da organização.
     * @param location A localização da organização.
     */
    public Organization(String name, String email, String password, String location) {
        super(name, email, password);
        this.setLocation(location);
    }

    /**
     * @return A localização da organização.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Define a localização da organização.
     * @param location A localização a ser definida.
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
     * @return A lista de alertas associados à organização.
     */
    public List<Alert> getAlerts() {
        return alerts;
    }

    /**
     * @return A lista de eventos associados à organização.
     */
    public List<Event> getEvents() {
        return events;
    }

    /**
     * Adiciona um alerta à lista de alertas associados à organização.
     * @param alert O alerta a ser adicionado.
     * @throws IllegalArgumentException Se o alerta for nulo.
     */
    public void addAlert(Alert alert){
        if (alert == null) throw new IllegalArgumentException("Alerta não pode ser nulo");

        this.alerts.add(alert);
    }

    /**
     * Remove um alerta da lista de alertas associados à organização.
     * @param alert O alerta a ser removido.
     * @throws IllegalArgumentException Se o alerta for nulo ou não existir na lista.
     */
    public void removeAlert(Alert alert){
        if (alert == null) throw new IllegalArgumentException("Alerta não pode ser nulo");
        if(!alerts.contains(alert)) throw new IllegalArgumentException("Alerta não existe");

        this.alerts.remove(alert);
    }

    /**
     * Cria um evento e o adiciona à lista de eventos associados à organização.
     * @param eventName O nome do evento.
     * @param description A descrição do evento.
     * @param eventDateTime A data e hora do evento.
     * @param location A localização do evento.
     * @throws IllegalArgumentException Se qualquer um dos parâmetros for nulo.
     */
    public void createEvent(String eventName, String description, LocalDateTime eventDateTime, String location){
        if (eventName == null) throw new IllegalArgumentException("Nome do evento não pode ser nulo");
        if (description == null) throw new IllegalArgumentException("Descrição do evento não pode ser nula");
        if (eventDateTime == null) throw new IllegalArgumentException("Data e hora do evento não podem ser nulas");

        try{
            Event event = new Event(eventName, description, eventDateTime, location, this);

            this.events.add(event);
            EventManager.addEvent(this, event);
        }catch (Exception e){
            System.out.println("Ocorreu um erro ao cadastrar o evento --> " + e.getMessage());
        }
    }
}