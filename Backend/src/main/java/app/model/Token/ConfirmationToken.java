package app.model.Token;

import app.model.User.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "user_id"
    )
    private User user;

    public ConfirmationToken(){};

    public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt, User user){

        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.confirmedAt = confirmedAt;
        this.user = user;

    }
    public String getToken(){return this.token;};

    public LocalDateTime getConfirmedAt(){
        return this.confirmedAt;
    }

    public LocalDateTime getExpiresAt(){
        return this.expiresAt;
    }

    public User getUser(){
        return this.user;
    }


}