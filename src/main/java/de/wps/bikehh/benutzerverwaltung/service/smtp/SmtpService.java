package de.wps.bikehh.benutzerverwaltung.service.smtp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class SmtpService {

    private JavaMailSender emailSender;
    private SpringTemplateEngine templateEngine;

    /**
     * @param javaMailSender
     */
    @Autowired
    public SmtpService(JavaMailSender javaMailSender, SpringTemplateEngine templateEngine) {
        this.emailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    /**
     * Is used to send mail.
     *
     * @param mail
     * @throws MessagingException
     */

    public void sendMail(Mail mail, String template) throws MessagingException, IOException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        //helper.addAttachment("logo.png", new ClassPathResource("memorynotfound-logo.png"));

        Context context = new Context();
        context.setVariables(mail.getModel());
        String html = templateEngine.process(template, context);

        helper.setTo(mail.getTo());
        helper.setText(html, true);
        helper.setSubject(mail.getSubject());

        emailSender.send(message);
    }

}
