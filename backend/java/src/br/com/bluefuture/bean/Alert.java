package br.com.bluefuture.bean;

/**
 * Esta classe representa um Alerta com uma localização e severidade.
 * Ela fornece métodos para obter e definir a localização e gravidade do alerta.
 */
public class Alert {
    private String location;
    private String severity;

    /**
     * Construtor padrão.
     */
    public Alert() {
    }

    /**
     * Construtor com parâmetros.
     * @param location A localização do alerta.
     * @param severity A gravidade do alerta.
     */
    public Alert(String location, String severity) {
        this.setLocation(location);
        this.setSeverity(severity);
    }

    /**
     * @return A localização do alerta.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Define a localização do alerta.
     * @param location A localização a ser definida. Não pode ser nula.
     * @throws IllegalArgumentException Se a localização for nula.
     */
    public void setLocation(String location) {
        if(location == null) throw new IllegalArgumentException("A localização do alerta não pode ser nula");
        this.location = location;
    }

    /**
     * @return A gravidade do alerta.
     */
    public String getSeverity() {
        return severity;
    }

    /**
     * Define a gravidade do alerta.
     * @param severity A gravidade a ser definida. Não pode ser nula.
     * @throws IllegalArgumentException Se a gravidade for nula.
     */
    public void setSeverity(String severity) {
        if(severity == null) throw new IllegalArgumentException("A gravidade não pode ser nula");
        this.severity = severity;
    }

    /**
     * @return A representação em string do alerta.
     */
    @Override
    public String toString() {
        return String.format("""
                -------------- %s ------------
                Localização: %s
                -------------------------------
                """, this.getSeverity(),this.getLocation());
    }
}