package br.com.bluefuture.bean;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Organization extends Account{
    private String location;
    private List<Alert> alerts;
    private List<Event> events;

    public Organization() {
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        try {
            if (location == null) throw new IllegalArgumentException("Invalid Argument --> Expected a non-null String");

            String cep;
            String regex = "\\d{5}-?\\d{3}";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(location);

            if(!(matcher.find())) throw new IllegalArgumentException("Invalid Argument --> CEP was not" +
                    " found in the String. Make sure it has 8 digits (Dash is optional)");

            cep = matcher.group(0);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(String.format("https://viacep.com.br/ws/%s/json", cep))).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) throw new IOException("Failed to connect with the API --> " +
                    response.statusCode());

            if(response.body().contains("\"erro\": true")) throw new IllegalArgumentException("Invalid Argument --> " +
                    "Given CEP was not valid");

            this.location = location;

        }catch (IllegalArgumentException e){
            System.err.println(e.getMessage());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Alert> getAlerts() {
        return alerts;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void addAlert(Alert alert){
        if (alert == null) throw new IllegalArgumentException("Alert can't be null");

        this.alerts.add(alert);
    }

    public void createEvent(String eventName, String description, LocalDateTime eventDateTime){
        if (eventName == null) throw new IllegalArgumentException("Event name can't be null");
        if (description == null) throw new IllegalArgumentException("Event description can't be null");
        if (eventDateTime == null) throw new IllegalArgumentException("Event date and time can't be null");

        Event event = new Event();
        try{
            event.setName(eventName);
            event.setDescription(description);
            event.setDateTime(eventDateTime);

            this.events.add(event);
        }catch (Exception e){
            System.err.println("Ocorreu um erro ao cadastrar o evento --> " + e.getMessage());
        }
    }
}
