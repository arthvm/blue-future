package br.com.bluefuture.bean;

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
        if(location == null) throw new IllegalArgumentException("Alert location can't be null");
        this.location = location;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        if(severity == null) throw new IllegalArgumentException("Severity can't be null");

        this.severity = severity;
    }

    @Override
    public String toString() {
        return String.format("""
                -------------- %s ------------
                Localizacao: %s
                -------------------------------
                """, this.getSeverity(),this.getLocation());
    }
}
