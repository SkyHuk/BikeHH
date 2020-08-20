package de.wps.bikehh.authentifizierung.material;

import org.hibernate.annotations.CreationTimestamp;

import de.wps.bikehh.benutzerverwaltung.material.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "token", name = "uniqueTokenConstraint")}
)
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String token;

    @OneToOne
    private User user;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;

    public Session(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public Session() {}

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
