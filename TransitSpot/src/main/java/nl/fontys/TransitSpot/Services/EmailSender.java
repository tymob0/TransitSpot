package nl.fontys.TransitSpot.Services;

public interface EmailSender {
    void send(String to, String subject, String body);
}
