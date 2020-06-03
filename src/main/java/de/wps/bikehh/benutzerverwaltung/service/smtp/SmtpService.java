package de.wps.bikehh.benutzerverwaltung.service.smtp;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * @param javaMailSender
     */
    @Autowired
    public SmtpService(JavaMailSender javaMailSender) {
        this.emailSender = javaMailSender;
    }

    /**
     * Is used to send mail.
     *
     * @param mail
     * @param template
     * @throws MessagingException
     */

    public void sendMail(Mail mail, Templates template) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, StandardCharsets.UTF_8.name());

        //helper.addAttachment("logo.png", new ClassPathResource("memorynotfound-logo.png"));

        Context context = new Context();
        context.setVariables(mail.getModel());

        SpringTemplateEngine templateEngine = springTemplateEngine();
        String html = templateEngine.process(template.getPath(), context);

        helper.setTo(mail.getTo());
        helper.setText(html, true);
        helper.setSubject(mail.getSubject());

        emailSender.send(message);
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
