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

public class Event {
    private String name;
    private String description;
    private LocalDateTime dateTime;
    private String location;
    private Organization organizer;
    private final List<User> participants = new ArrayList<>();

    public Event() {
    }

    public Event(String name, String description, LocalDateTime dateTime, String location, Organization organizer) {
        this.setName(name);
        this.setDescription(description);
        this.setDateTime(dateTime);
        this.setLocation(location);
        this.setOrganizer(organizer);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) throw new IllegalArgumentException("Name of the event can't be empty");

        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        ZoneId zoneId = ZoneId.of("America/Sao_Paulo");
        LocalDateTime currDateTime = LocalDateTime.now(zoneId);

        if(dateTime.isBefore(currDateTime)) throw new IllegalArgumentException("Can't create an event in the past");

        this.dateTime = dateTime;
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
            System.out.println(e.getMessage());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Organization getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Organization organizer) {
        if (organizer == null) throw new IllegalArgumentException("Organzier can't be null");

        this.organizer = organizer;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void addParticipant(User user){
        if (user == null) throw new IllegalArgumentException("User can't be null");

        participants.add(user);
    }

    public int getParticipantCount(){
        return participants.size();
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy -- HH:mm");
        return this.getName() + "(" + getDateTime().format(formatter) + " em " + getLocation() + ")";
    }
}
