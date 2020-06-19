package de.wps.bikehh.benutzerverwaltung.service.smtp;

import de.wps.bikehh.benutzerverwaltung.service.Logger;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
public class SmtpService {

    public enum Templates {
        RESET("email/reset-template"),
        VERIFY("email/verify-template");

        private String path;

        Templates(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }

    private JavaMailSender emailSender;

    @Value("${spring.profiles.active}")
    private String enviroment;


    @Autowired
    public SmtpService(JavaMailSender javaMailSender) {
        this.emailSender = javaMailSender;
    }

    public void sendMail(Mail mail, Templates template) {
        if (enviroment.equals("local")) {
            return;
        }

        MimeMessage message = emailSender.createMimeMessage();

        Context context = new Context();
        context.setVariables(mail.getModel());
        context.setVariable("logo", "logo");

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());


            SpringTemplateEngine templateEngine = springTemplateEngine();
            String html = templateEngine.process(template.getPath(), context);

            //@TODO set name of sender to "byclistics" or what ever the name of the app is gonna be
            helper.setTo(mail.getTo());
            helper.setText(html, true);
            helper.setSubject(mail.getSubject());

            helper.addInline("logo", new ClassPathResource("/images/logo.png"), "image/png");
            emailSender.send(message);
        } catch (MessagingException e) {

            String error = String.format("failed to send mail to %s. Error was: %s", mail.getTo(), e.getMessage());
            Logger.logger.error(error);
        }


    }

    private SpringTemplateEngine springTemplateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(htmlTemplateResolver());
        templateEngine.addDialect(new LayoutDialect());
        return templateEngine;
    }


    private ITemplateResolver htmlTemplateResolver() {
        ClassLoaderTemplateResolver emailTemplateResolver = new ClassLoaderTemplateResolver();
        emailTemplateResolver.setPrefix("/templates/");
        emailTemplateResolver.setSuffix(".html");
        emailTemplateResolver.setTemplateMode("HTML");
        emailTemplateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        return emailTemplateResolver;
    }
}
