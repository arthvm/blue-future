package br.com.bluefuture.bean;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Alert {
    private String location;
    private String severity;

    public Alert() {
    }

    public Alert(String location, String severity) {
        this.setLocation(location);
        this.setSeverity(severity);
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

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        if(severity == null) throw new IllegalArgumentException("Severity can't be null");

        this.severity = severity;
    }
}
