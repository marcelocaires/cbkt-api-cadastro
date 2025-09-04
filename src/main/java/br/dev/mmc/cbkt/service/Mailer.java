package br.dev.mmc.cbkt.service;

public interface Mailer {
    void send(String to, String subject, String htmlBody);
}
