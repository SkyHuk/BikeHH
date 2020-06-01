package de.wps.bikehh.benutzerverwaltung.material;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Reset {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;

    @NotNull
    private String token;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;

    /*@OneToOne
    @MapsId
    @JoinColumn(name = "userId")
    private User user;*/

    public Reset(Long userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public Reset(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
