package main.controller;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class EmailController {
    
    private final JavaMailSender javaMailSender;

    public EmailController(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @RequestMapping(value = "/send")
    public String send(){
// адрес который увидит получатель в поле ОТ:
        String from = "safonov.blog@yandex.ru";
// адрес получателя, куда отправляем письмо (email пользователя)
        String to = "elektronik1989@yandex.ru";

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);
        message.setTo(to);
        message.setSubject("Это тема письма");
        message.setText("Воу! А это текст письма! Это просто текст, без оформления и HTML!");

        javaMailSender.send(message);

        return "Письмо отправлено в " + LocalDateTime.now().toString();
    }

}
