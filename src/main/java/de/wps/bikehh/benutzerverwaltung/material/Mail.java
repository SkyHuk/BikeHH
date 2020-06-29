package de.wps.bikehh.benutzerverwaltung.material;

import java.util.Map;

public class Mail {

    private String to;
    private String subject;
    private Map<String,Object> model;

    public Mail(String to, String subject) {
        this.to = to;
        this.subject = subject;
    }


    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setModel(Map model) {
        this.model = model;
    }

    public Map<String,Object> getModel(){
        return model;
    }

    @Override
    public String toString() {
        return "Mail{" +
                "to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
}
