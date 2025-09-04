package br.dev.mmc.cbkt.service;

import org.springframework.stereotype.Service;

@Service
public class NoopMailer implements Mailer {
    @Override
    public void send(String to, String subject, String htmlBody) {
        // Implementação real deve usar JavaMailSender/SES/etc.
    }
}
