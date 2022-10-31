package org.javaproteam27.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.response.ComplexRs;
import org.javaproteam27.socialnetwork.model.dto.response.RegisterRs;
import org.javaproteam27.socialnetwork.model.entity.Person;
import org.javaproteam27.socialnetwork.repository.PersonRepository;
import org.javaproteam27.socialnetwork.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final JwtTokenProvider jwtTokenProvider;
    private final PersonRepository personRepository;
    @Value("${mailing-service.email}")
    private String fromEmail;

    public RegisterRs putEmail(String token) {

        String url = "http://195.133.48.174:8080/change-password?token=";

        String email = jwtTokenProvider.getUsername(token);
        Person person = personRepository.findByEmail(email);
        String newToken = UUID.randomUUID().toString();
        person.setChangePasswordToken(newToken);
        personRepository.editPasswordToken(person);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(email);
        message.setSubject("Subject: Simple Mail");
        message.setText("Ссылка для восстановления eMail: " + url + newToken);

        mailSender.send(message);

        var data = ComplexRs.builder().message("ok").build();

        return RegisterRs.builder()
                .error("string")
                .data(data)
                .build();
    }
}
